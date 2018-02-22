package services;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/")
public class RestService {

    @GET
    @Path("/targetdbs")
    @Produces("application/json")
    public String targetdbs() {
        JSONArray result = new JSONArray();
        HashMap<Integer, String> hm = BusinessRuleService.getInstance().getDaoservice().getTargetDatabases();
        for(Integer key : hm.keySet()){
            JSONObject jo = new JSONObject();
            jo.append("id", key);
            jo.append("name", hm.get(key));
            result.put(jo);
        }
        return result.toString();
    }

    @GET
    @Path("/targettables")
    @Produces("application/json")
    public String targettables(@QueryParam("id") Integer id) {
        ArrayList<String> tables = (ArrayList<String>) BusinessRuleService.getInstance().getDaoservice().getTargetTables(id);
        JSONObject jo = new JSONObject();

        if(tables == null || tables.size() == 0){
            return jo.append("message","Invalid target id / invalid database details.").toString();
        }

        for(String s: tables){
            jo.append("table_names", s);
        }
        return jo.toString();
    }

    @GET
    @Path("/tablecolumns")
    @Produces("application/json")
    public String tablecolumns(@QueryParam("dbid") Integer dbid, @QueryParam("tablename") String tablename) {
        HashMap<String, String> cols = BusinessRuleService.getInstance().getDaoservice().getTargetColumns(dbid, tablename);

        JSONObject result = new JSONObject();
        for(String s : cols.keySet()){
            JSONObject jo = new JSONObject();
            jo.append("column", s);
            jo.append("datatype", cols.get(s));
            result.append("columns", jo);
        }
        return result.toString();
    }

    @GET
    @Path("/generate")
    @Produces("application/json")
    public String generate(@QueryParam("id") Integer id) {
        String result = BusinessRuleService.getInstance().generate(id);

        if(result == "")
            result = "Businessrule not defined properly.";
        JSONObject jo = new JSONObject();
        jo.append("result", result);
        return jo.toString();
    }

    @GET
    @Path("/execute")
    @Produces("application/json")
    public String execute(@QueryParam("id") int id) {
        Boolean result = BusinessRuleService.getInstance().executeBusinessRule(id);
        JSONObject jo = new JSONObject();
        jo.append("executed", result);
        return jo.toString();
    }

}
