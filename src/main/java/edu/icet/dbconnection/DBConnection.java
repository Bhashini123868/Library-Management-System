package edu.icet.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final String URL = "jdbc:mysql://localhost/librarydb";
    private final String userName = "root";
    private final String password = "1234";

    private DBConnection() {
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, userName, password);
    }

}
