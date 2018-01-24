package services;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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

//    @GET
//    @Path("/targetdbtables")
//    @Produces("application/json")
//    public String targetdbs(@QueryParam("id") Integer id) {
//
//    }

    @GET
    @Path("/businessrule")
    @Produces("application/json")
    public String targetdbs(@QueryParam("id") Integer id) {
        JSONArray result = new JSONArray();
        ResultSet rs = BusinessRuleService.getInstance().getDaoservice().getBusinessRule(id);
        try {
            while(rs.next()){
                result.put(rs.getString("RULENAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

//    @POST
//    @Path("/generate")
//    @Produces("application/html")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public String generateRule(@FormParam("Entity") String ent,
//                               @FormParam("Attribute") String attr,
//                               @FormParam("Persistence") String persistence,
//                               @FormParam("Timing") String timing,
//                               @FormParam("Category") String category,
//                               @FormParam("PrimaryVal") String primaryVal,
//                               @FormParam("SecondVal") String secondVal){
//    }
}
