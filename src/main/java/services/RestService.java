package services;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
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
    @Path("/generate")
    @Produces("application/json")
    public String generate(@QueryParam("id") Integer id) {
        String result = BusinessRuleService.getInstance().generate(id);

        JSONObject jo = new JSONObject();
        jo.append("code", result);
        return jo.toString();
    }

    @GET
    @Path("/execute")
    @Produces("application/json")
    public String generate(@QueryParam("id") int id) {
        Boolean result = BusinessRuleService.getInstance().executeBusinessRule(id);
        JSONObject jo = new JSONObject();
        jo.append("executed", result);
        return jo.toString();
    }

}
