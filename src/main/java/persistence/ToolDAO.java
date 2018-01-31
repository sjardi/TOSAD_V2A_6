package persistence;

import java.lang.annotation.Target;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yorick on 23/01/2018.
 */
public class ToolDAO implements BaseDAO{
    private String username = "tosad_2017_2a_team6";
    private String password = "tosad_2017_2a_team6";
    private String url = "jdbc:oracle:thin:@//ondora02.hu.nl:8521/cursus02.hu.nl";
    private Connection connection;

    public ToolDAO(){
    }

    public HashMap<Integer, String> getTargetDatabases(){
        // open connection if connection is closed
        try {
            if(connection.isClosed())
                openConnection(this.url, this.username, this.password);
        } catch (Exception e) {
        }
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        try {
            String sql = "select DB_ID, DBNAME from TARGETDATABASE";
            PreparedStatement pr = connection.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                String name = rs.getString("DBNAME");
                Integer id = rs.getInt("DB_ID");
                hm.put(id, name);
            }
        } catch (Exception e) {

        }
        return hm;
    }

    public HashMap<String, String> getTargetDatabase(Integer id){
        // open connection if connection is closed
        try {
            if(connection.isClosed())
                openConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HashMap<String, String> hm = new HashMap<String, String>();
        try {
            String sql = "select DBNAME, USERNAME, PASSWORD, URL, TYPE from TARGETDATABASE WHERE DB_ID = ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                hm.put("DBNAME", rs.getString("DBNAME"));
                hm.put("USERNAME", rs.getString("USERNAME"));
                hm.put("PASSWORD", rs.getString("PASSWORD"));
                hm.put("URL", rs.getString("URL"));
                hm.put("TYPE", rs.getString("TYPE"));
            }
        } catch (Exception e) {

        }
        return hm;
    }

    public ResultSet getBusinessRule(Integer id){
        // open connection if connection is closed
        try {
            if(connection.isClosed())
                openConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "select BUSINESSRULES.RULENAME as RULENAME,\n" +
                    "    BUSINESSRULES.EXECUTED as EXECUTED,\n" +
                    "    BUSINESSRULES.RULETYPE as RULETYPE,\n" +
                    "    TARGETVALUES.V_VALUE as V_VALUE,\n" +
                    "    TARGETVALUES.POSITION as V_POSITION,\n" +
                    "    TARGETCOLUMN.V_TABLE as T_TABLE,\n" +
                    "    TARGETCOLUMN.V_COLUMN as T_COLUMN,\n" +
                    "    TARGETCOLUMN.POSITION as T_POSITION,\n" +
                    "    TEMPLATES.CODE as MAINCODE,\n" +
                    "    MASTERTEMPLATE.START_CODE as START_CODE,\n" +
                    "    MASTERTEMPLATE.END_CODE as END_CODE,\n" +
                    "    BUSINESSRULES.ID as ID,\n" +
                    "    TARGETDATABASE.DBNAME as DBNAME,\n" +
                    "    TARGETDATABASE.USERNAME as USERNAME,\n" +
                    "    TARGETDATABASE.PASSWORD as PASSWORD,\n" +
                    "    TARGETDATABASE.URL as URL,\n" +
                    "    TARGETDATABASE.TYPE as DBTYPE,\n" +
                    "    MASTERTEMPLATE.TIMING as TIMING,\n" +
                    "    MASTERTEMPLATE.TRIGGEREVENT as TRIGGEREVENT,\n" +
                    "    TARGETOPERATOR.OPERATOR as OPERATOR,\n" +
                    "    SQL.SQL_CODE as SQLCODE\n" +
                    " from TIMING TIMING,\n" +
                    "    TRIGGEREVENT TRIGGEREVENT,\n" +
                    "    MASTERTEMPLATE MASTERTEMPLATE,\n" +
                    "    TEMPLATES TEMPLATES,\n" +
                    "    TARGETDATABASE TARGETDATABASE,\n" +
                    "    TARGETCOLUMN TARGETCOLUMN,\n" +
                    "    RULETYPES RULETYPES,\n" +
                    "    TARGETVALUES TARGETVALUES,\n" +
                    "    BUSINESSRULES BUSINESSRULES,\n" +
                    "    TARGETOPERATOR TARGETOPERATOR,\n" +
                    "    SQL SQL\n" +
                    " where BUSINESSRULES.RULETYPE=RULETYPES.RULETYPE\n" +
                    "    and TARGETCOLUMN.ID=BUSINESSRULES.ID\n" +
                    "    and TARGETDATABASE.DB_ID=BUSINESSRULES.DB_ID\n" +
                    "    and TARGETVALUES.ID=BUSINESSRULES.ID\n" +
                    "    and TEMPLATES.RULETYPE=RULETYPES.RULETYPE\n" +
                    "    and TIMING.TIMING=MASTERTEMPLATE.TIMING\n" +
                    "    and TRIGGEREVENT.TRIGGEREVENT=MASTERTEMPLATE.TRIGGEREVENT\n" +
                    "    and BUSINESSRULES.MASTER_ID=MASTERTEMPLATE.MASTER_ID\n" +
                    "    and TARGETOPERATOR.ID = BUSINESSRULES.ID\n" +
                    "    and SQL.SQL_ID = BUSINESSRULES.SQL_ID\n" +
                    "    and BUSINESSRULES.ID = ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            return rs;
            }
         catch(Exception e) {

        }
        try {
            connection.close();
        } catch (Exception e) {

        }
        return null;
    }




    public Connection openConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
        }
        connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }

    public Connection openConnection(String url, String username, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
        }
        connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBusinessRule(int id, boolean executed) {
        try {
            if(connection.isClosed())
                openConnection(this.url, this.username, this.password);

            PreparedStatement ps = connection.prepareStatement("UPDATE BUSINESSRULES SET EXECUTED = ? WHERE ID = ?");
            ps.setBoolean(1, executed);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}