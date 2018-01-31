package persistence;

import java.sql.*;
import java.util.List;

/**
 * Created by Yorick on 23/01/2018.
 */
public class TargetDAO implements BaseDAO{
    private String dbname;
    private String username;
    private String password;
    private String url;
    private String dbtype;
    private Connection connection;

    public TargetDAO(String dbname,String username, String password, String url, String dbtype){
        this.dbname = dbname;
        this.username = username;
        this.password = password;
        this.url = url;
        this.dbtype = dbtype;
    }

    public List<String> getTables() {
        return null;
    }

    public void executeBusinessRule(String sql){
        try {
            connection = openConnection(url, username, password);
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getColumns(String table){
        return null;
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
            connection = DriverManager.getConnection(url, username.toLowerCase(), password.toLowerCase());
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }

    public boolean triggerExists(String triggername){
        try {
            connection = openConnection(url, username, password);
            Statement stmt = connection.createStatement();
            return stmt.execute("select count(*) from user_triggers where trigger_name = '"+triggername+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public String getDbname() {
        return dbname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getDbtype() {
        return dbtype;
    }
}
