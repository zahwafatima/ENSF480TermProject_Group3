import Controller.DatabaseConnection;
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
    private DatabaseConnection db;


    public GuestUser(boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        super(isRegistered, name, address, phoneNumber, email, pass, accessLevel);

    }

    public UserController(DatabaseConnection db) {
        this.db = db;
    }

    public void subscribeToMembership() {
        setIsRegistered(!getIsRegistered()); 
        System.err.println("Guest user has subscribed to membership");
    
        try (Connection connection = db.getConnection()) {
            String sql = "UPDATE FLIGHTDB.USERS SET isRegistered = ? WHERE userID = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, getIsRegistered());
                preparedStatement.setInt(2, getUserId());  // Assuming you have a method to get the user ID
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("User was registered in the user table successfully");
                } else {
                    System.out.println("User failed to be registered in the user table");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    



}

