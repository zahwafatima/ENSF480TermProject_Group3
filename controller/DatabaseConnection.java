package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost/flightdb";
    private static final String USERNAME = "flightReserve";
    private static final String PASSWORD = "password";

    private static DatabaseConnection onlyInstance;
    private Connection dbConnect;

    private DatabaseConnection(){
        createConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static DatabaseConnection getOnlyInstance(){
        if (onlyInstance == null){
            onlyInstance = new DatabaseConnection();
        }
        return onlyInstance;
    }

    public void createConnection(String link, String username, String password){
        try{
            dbConnect = DriverManager.getConnection(link, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

