import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import java.util.HashMap;

public class DatabaseConnector {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/aircraftrezerv";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "shubham123";
    private Connection dbConnect;
    private ResultSet results;
    public DatabaseConnector(){
        createConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
    public void createConnection(String link, String username, String password){
        try{
            //CHECK UR CREDENTIALS PLEASE BEFORE RUNNING
            dbConnect = DriverManager.getConnection(link, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public HashMap<Integer, User> readUser() {
        //Returns Data From User in HashMap
        HashMap<Integer, User> userList = new HashMap<>();
        try {
            Statement myStmt = dbConnect.createStatement();

            results = myStmt.executeQuery("SELECT * FROM USERS");
            while(results.next()){
                User theUser = new User(results.getInt("UserID"),
                        results.getString("Name"), results.getString("Email"),results.getDate("DateOfBirth"),results.getString("PhoneNumber"),results.getString("Address"));
                userList.put(results.getInt("UserID"), theUser);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }
}
