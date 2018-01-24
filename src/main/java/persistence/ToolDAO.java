package persistence;

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
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
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
            String sql = "select BUSINESSRULES.ID as ID,\n" +
                    "    BUSINESSRULES.RULENAME as RULENAME,\n" +
                    "    BUSINESSRULES.TIMING as TIMING,\n" +
                    "    BUSINESSRULES.EXECUTED as EXECUTED,\n" +
                    "    BUSINESSRULES.SQL_ID as SQL_ID,\n" +
                    "    BUSINESSRULES.MASTER_ID as MASTER_ID,\n" +
                    "    BUSINESSRULES.RULETYPE as RULETYPE,\n" +
                    "    BUSINESSRULES.DB_ID as DB_ID,\n" +
                    "    TEMPLATES.TYPE as TYPE,\n" +
                    "    TEMPLATES.CODE as CODE,\n" +
                    "    TARGETVALUES.V_VALUE as V_VALUE,\n" +
                    "    TARGETVALUES.POSITION as POSITION,\n" +
                    "    TARGETCOLUMN.V_TABLE as V_TABLE,\n" +
                    "    TARGETCOLUMN.V_COLUMN as V_COLUMN,\n" +
                    "    TARGETCOLUMN.POSITION as POSITION_COL,\n" +
                    "    TARGETDATABASE.DBNAME as DBNAME,\n" +
                    "    TARGETDATABASE.USERNAME as USERNAME,\n" +
                    "    TARGETDATABASE.PASSWORD as PASSWORD,\n" +
                    "    TARGETDATABASE.URL as URL,\n" +
                    "    TARGETDATABASE.TYPE as DBTYPE \n" +
                    " from TARGETDATABASE TARGETDATABASE,\n" +
                    "    TARGETCOLUMN TARGETCOLUMN,\n" +
                    "    TARGETVALUES TARGETVALUES,\n" +
                    "    RULETYPES RULETYPES,\n" +
                    "    TEMPLATES TEMPLATES,\n" +
                    "    BUSINESSRULES BUSINESSRULES \n" +
                    " where BUSINESSRULES.RULETYPE=RULETYPES.RULETYPE\n" +
                    "    and TEMPLATES.RULETYPE=RULETYPES.RULETYPE\n" +
                    "    and TARGETVALUES.ID=BUSINESSRULES.ID\n" +
                    "    and TARGETCOLUMN.ID=BUSINESSRULES.ID\n" +
                    "    and TARGETDATABASE.DB_ID=BUSINESSRULES.DB_ID and BUSINESSRULES.ID = ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            return rs;
            }
         catch(SQLException e) {
            e.printStackTrace();
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
}