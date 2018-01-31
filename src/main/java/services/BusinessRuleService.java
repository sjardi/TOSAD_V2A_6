package services;

import model.*;
import persistence.DAOService;
import persistence.TargetDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yorick on 23/01/2018.
 */
public class BusinessRuleService {

    private static BusinessRuleService instance;
    private DAOService daoservice;
    private TemplateParser templateParser;

    public BusinessRuleService() {
        this.daoservice = new DAOService();
        this.templateParser = new TemplateParser();
    }

    public static BusinessRuleService getInstance() {
        if(instance == null){
            instance = new BusinessRuleService();
        }
        return instance;
    }

    public DAOService getDaoservice() {
        return daoservice;
    }

    public String generate(Integer id){
        ResultSet rs = this.getDaoservice().getBusinessRule(id);
        String sql = this.templateParser.parse(this.ResultSetToBusinessRule(rs));

        return sql;
    }

    public boolean executeBusinessRule(int id){
        ResultSet rs = this.getDaoservice().getBusinessRule(id);
        BusinessRule br = this.ResultSetToBusinessRule(rs);
        String sql = this.templateParser.parse(br);

        daoservice.executeBusinessRule(sql, br.getTargetdb());
        boolean result = daoservice.triggerExists(br.getRuleType().getTypeName() + "_" + br.getId());
        if(result)
            daoservice.updateBusinessRule(id, true);
        return result;
    };

    private BusinessRule ResultSetToBusinessRule(ResultSet rs){
        BusinessRule br;
        Integer id = 0;
        String ruleName = "";
        String timing = "";
        String ruleType = "";
        String maincode = "";
        String begincode = "";
        String endcode = "";
        String triggerevent = "";

        ArrayList<Value> values = new ArrayList<Value>();
        ArrayList<Table> tables = new ArrayList<Table>();
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<Operator> operators = new ArrayList<Operator>();

        String dbname = "";
        String username = "";
        String password = "";
        String dbtype = "";
        String url = "";
        String sqlcode = "";

        try {
            while(rs.next()){

                id = rs.getInt("ID");
                ruleName = rs.getString("RULENAME");
                timing = rs.getString("TIMING");
                ruleType = rs.getString("RULETYPE");
                begincode = rs.getString("START_CODE");
                maincode = rs.getString("MAINCODE");
                endcode = rs.getString("END_CODE");
                triggerevent = rs.getString("TRIGGEREVENT");


                values.add(new Value(rs.getInt("V_POSITION"), rs.getString("V_VALUE")));
                columns.add(rs.getString("T_COLUMN"));
                tables.add(new Table(rs.getString("T_TABLE"), null, rs.getInt("T_POSITION")));
                operators.add(new Operator(rs.getString("OPERATOR")));

                dbname = rs.getString("DBNAME");
                username = rs.getString("USERNAME");
                password = rs.getString("PASSWORD");
                url = rs.getString("URL");
                dbtype = rs.getString("DBTYPE");
                sqlcode = rs.getString("SQLCODE");

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ArrayList<Table> tb = new ArrayList<Table>();
        for(int i = 0; i < tables.size(); i++)
            tb.add(new Table(tables.get(i).getName(), columns, tables.get(i).getPosition()));

        br = new BusinessRule(id, ruleName, new BusinessRuleType(ruleType, new Category(""), new Template(maincode, begincode, endcode, timing, triggerevent), operators), values, tb, false, new TargetDAO(dbname, username, password, url, dbtype), sqlcode);
        return br;
    }
}
