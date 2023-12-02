package Controller;


import Controller.DatabaseConnection;
import Controller.UserController;
import Entity.Address;
import Entity.Name;
import Entity.Ticket;
import Entity.User;
import Entity.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GuestUser extends User{


    public GuestUser(int userID, boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        super(userID, isRegistered, name, address, phoneNumber, email, pass, accessLevel);

    }

    // public GuestUser(DatabaseConnection db) {
    //     this.db = db;
    // }

    public void subscribeToMembership() {
        setIsRegistered(true); 
        System.err.println("Guest user has subscribed to membership");
    
            String sql = "UPDATE FLIGHTDB.USERS SET isRegistered = ? WHERE userID = ?";
            
            try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, getUserID()); 
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("User was registered in the user table successfully");
                } else {
                    System.out.println("User failed to be registered in the user table");
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }}
    }


