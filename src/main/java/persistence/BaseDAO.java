package persistence;

import java.sql.*;

interface BaseDAO {

    public Connection openConnection(String url, String username, String password);
    public Connection getConnection();
    public void closeConnection();
}
