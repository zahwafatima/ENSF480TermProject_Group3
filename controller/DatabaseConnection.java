package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost/flightdb";
    private static final String USERNAME = "flightReserve";
    private static final String PASSWORD = "password";

    private static DatabaseConnection onlyInstance;
    public static Connection dbConnect;

    public DatabaseConnection() {
        createConnection();
    }

    public static DatabaseConnection getOnlyInstance() {
        if (onlyInstance == null) {
            onlyInstance = new DatabaseConnection();
        }
        return onlyInstance;
    }

    private void createConnection() {
        try {
            dbConnect = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Handle the exception appropriately (log it, throw it, etc.)
            throw new RuntimeException("Failed to create a database connection", e);
        }
    }

    public Connection getConnection() {
        return dbConnect;
    }
}
