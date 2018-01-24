package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        this.connection = this.openConnection(url, username, password);
    }

    public List<String> getTables() {
        return null;
    }

    public void executeBusinessRule(String sql){

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
