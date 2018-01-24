package services;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.TargetDAO;

import javax.ws.rs.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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

//    @GET
//    @Path("/targetdbtables")
//    @Produces("application/json")
//    public String targetdbs(@QueryParam("id") Integer id) {
//
//    }

//    @GET
//    @Path("/businessrule")
//    @Produces("application/json")
//    public String targetdbs(@QueryParam("id") Integer id) {
//        JSONArray result = new JSONArray();
//        ResultSet rs = BusinessRuleService.getInstance().getDaoservice().getBusinessRule(id);
//        try {
//            while(rs.next()){
//                result.put(rs.getString("RULENAME"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }

    @GET
    @Path("/generate")
    @Produces("application/json")
    public String generate(@QueryParam("id") Integer id) {
        ResultSet rs = BusinessRuleService.getInstance().getDaoservice().getBusinessRule(id);

        BusinessRule br;
        String ruleName = "";
        String timing = "";
        String ruleType = "";
        String code = "";


        ArrayList<Value> values = new ArrayList<Value>();
        ArrayList<String> tables = new ArrayList<String>();
        ArrayList<String> columns = new ArrayList<String>();

        String dbname = "";
        String username = "";
        String password = "";
        String dbtype = "";
        String url = "";
        try {
            while(rs.next()){

                ruleName = rs.getString("RULENAME");
                timing = rs.getString("TIMING");
                ruleType = rs.getString("RULETYPE");
                code = rs.getString("CODE");

                values.add(new Value(rs.getInt("POSITION"), rs.getString("V_VALUE")));
                columns.add(rs.getString("V_COLUMN"));
                tables.add(rs.getString("V_TABLE"));

                dbname = rs.getString("DBNAME");
                username = rs.getString("USERNAME");
                password = rs.getString("PASSWORD");
                url = rs.getString("URL");
                dbtype = rs.getString("DBTYPE");

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        ArrayList<Table> tb = new ArrayList<Table>();
        for(int i = 0; i < tables.size(); i++)
            tb.add(new Table(tables.get(i), columns, i));

        br = new BusinessRule(id, ruleName, timing, new BusinessRuleType(ruleType, new Category(""), new Template(code), null), values, tb, false, new TargetDAO(dbname, username, password, url, dbtype));
        TemplateParser tp = new TemplateParser();
        String sql = tp.parse(br);

        JSONObject jo = new JSONObject();
        jo.append("code", sql);
        return jo.toString();
    }

}
