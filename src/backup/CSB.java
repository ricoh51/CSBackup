package backup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Model for the project datas
 * @author Eric Marchand
 */
public class CSB {
    private static final String PROJECTDIR = "Project dir";
    private static final String IGNORES = "Ignores";
    private static final String WHERES = "Wheres";
    private static final String ZIPNAME = "Zip name";
    private static final String KEEPEMPTY = "Keep empty";
    
    /** root directory to zip **/
    public String projectDir;
    /** all directory to ignore **/
    public List<String> ignores;
    /** all copies of zip **/
    public List<String> wheres;
    /** base name for zip **/
    public String zipName;
    /** Keep ignored dir empties **/
    public boolean keepEmpty;
    
    /** used for beautify json **/
    private final ScriptEngine scriptEngine;
    
    /**
     * Constructor
     */
    public CSB(){
        ignores = new ArrayList<>();
        wheres = new ArrayList<>();
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
    }
    
    public void reset(){
        projectDir = "";
        ignores.clear();
        wheres.clear();
        zipName = "";
        keepEmpty = true;
    }

    /**
     * Parse the json string and fill all properties
     */
    public void fromJSON(String json) throws BackupException{
        JSONParser parser = new JSONParser();
        try {
            Object root = parser.parse(json);
            JSONObject jsonObject = (JSONObject) root;
            projectDir = (String) jsonObject.get(PROJECTDIR);
            //System.out.println("backup.CSB.fromJSON() projectdir  : " + projectDir);
            JSONArray array = (JSONArray) jsonObject.get(IGNORES);
            Iterator<String> iterator = array.iterator();
            while (iterator.hasNext()) {
                ignores.add(iterator.next());
            }
            array = (JSONArray) jsonObject.get(WHERES);
            iterator = array.iterator();
            while (iterator.hasNext()) {
                wheres.add(iterator.next());
            }
            zipName = (String) jsonObject.get(ZIPNAME);
            Object o = jsonObject.get(KEEPEMPTY);
            if (o != null)
                keepEmpty = (boolean) o;
            else 
                keepEmpty = true;
        } catch (ParseException ex) {
            System.err.println("backup.CSB.fromJSON() error : " + ex.getMessage());
            throw new BackupException("JSON error ! : " + ex.getMessage());
        }
        
    }
    
    /**
     * @return a json string 
     */
    public String toJSON() throws BackupException{
        JSONObject root = new JSONObject();
        root.put(PROJECTDIR, projectDir);
        root.put(ZIPNAME, zipName);
        JSONArray list = new JSONArray();
        for (String ignore : ignores){
            list.add(ignore);
        }
        root.put(IGNORES, list);
        list = new JSONArray();
        for (String where : wheres){
            list.add(where);
        }
        root.put(WHERES, list);
        String json = root.toJSONString(); 
        root.put(KEEPEMPTY, keepEmpty);
        return prettyJSON(json); 
    }
    
     /**
     * JSON beautifier
     * Use Javascript
     */
    private String prettyJSON(String json) throws BackupException {
        scriptEngine.put("jsonString", json);
        try {
            scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, 4)");
        } catch (ScriptException ex) {
            System.err.println("CSB prettyJSON error : " + ex.getMessage());
            throw new BackupException("JSON error ! : " + ex.getMessage());
        }
        return (String) scriptEngine.get("result");
    }
    
    
}
