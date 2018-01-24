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
            String sql = "";
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