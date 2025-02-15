package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_Connection {
    private static Db_Connection instance;
    @Getter

    private Connection connection;
    private Db_Connection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/librarymanagement";
        String userName = "root";
        String password = "1234";
        connection = DriverManager.getConnection(URL, userName, password);

    }

    public static Db_Connection getInstance() throws SQLException {
        return instance == null ? instance = new Db_Connection() : instance;
    }



}
