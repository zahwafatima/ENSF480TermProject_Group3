
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost/flightdb";
    private static final String USERNAME = "flightReserve";
    private static final String PASSWORD = "password";
    public Connection dbConnect;
    private ResultSet results;

    public DatabaseConnection(){
        createConnection(JDBC_URL, USERNAME, PASSWORD);
    }
    public void createConnection(String link, String username, String password){
        try{
            dbConnect = DriverManager.getConnection(link, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

