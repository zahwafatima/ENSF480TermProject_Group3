package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Entity.Seat;
import Entity.User;

    public class UserController {
    
        private DatabaseConnection db;
    
        public UserController(DatabaseConnection db) {
            this.db = db;
        }
        //add a new user to the database 
        public void addUserToDB(User user) {
            try (Connection connection = db.getConnection()) {
                String sql = "INSERT INTO FLIGHTDB.USERS (isRegistered, firstName, lastName, street, city, country, email, pass, phoneNumber, accessLevel) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, "Yes"); // Assuming isRegistered should be set to "Yes" for a new user
                    preparedStatement.setString(2, user.getName().getFirstName());
                    preparedStatement.setString(3, user.getName().getLastName());
                    preparedStatement.setString(4, user.getAddress().getStreet());
                    preparedStatement.setString(5, user.getAddress().getCity());
                    preparedStatement.setString(6, user.getAddress().getCountry());
                    preparedStatement.setString(7, user.getLogin().getEmail());
                    preparedStatement.setString(8, user.getLogin().getPassword());
                    preparedStatement.setString(9, String.valueOf(user.getPhoneNumber()));
                    preparedStatement.setString(10, user.getAccessLevel());
    
                    int rowsAffected = preparedStatement.executeUpdate();
    
                    if (rowsAffected > 0) {
                        System.out.println("User added to the database successfully.");
                    } else {
                        System.out.println("Failed to add user to the database.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //returns a map of all of the seats associated with an aircraft 
        public Map<String, Seat> browseSeatMap() {
            Map<String, Seat> seatMap = new HashMap<>();

            try (Connection connection = db.getConnection()) {
                String sql = "SELECT * FROM FLIGHTDB.SEATS";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            // Assuming Seat class has a constructor that takes relevant parameters
                            Seat seat = new Seat(
                                    resultSet.getString("seatNumber"),
                                    resultSet.getString("seatClass"),
                                    resultSet.getBoolean("isBooked"),
                                    resultSet.getDouble("price")
                            );
                            seatMap.put(seat.getSeatNumber(), seat);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return seatMap;
        }

        //find accessLevel after login 
        public static String getUserAccessLevel(Login login) {
        String email = login.getEmail();
        String password = login.getPassword();

        try (Connection connection = db.getConnection()) {
            String sql = "SELECT accessLevel FROM USERS WHERE email = ? AND pass = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // User found, return the access level
                        return resultSet.getString("accessLevel");
                    } else {
                        // No user found with the given email and password
                        return "No user found";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error retrieving user access level";
    }

        


    
}
