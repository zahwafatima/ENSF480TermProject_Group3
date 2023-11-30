

// public class FlightReservation{
//     public static void main(String[] args) {
//         DatabaseConnection dbConnection = new DatabaseConnection();

//         if (dbConnection.dbConnect != null) {
//             System.out.println("Connection to the database is successful!");
//             SwingUtilities.invokeLater(() -> {
//                 AirlineReservationSystem frame = new AirlineReservationSystem();
//                 frame.setVisible(true);
//             });
//         } else {
//             System.out.println("Failed to connect to the database. Check your connection details.");
//         }
//     }
// }



import Controller.DatabaseConnection;
import Controller.DatabaseConnection;
import javax.swing.*;
//import Boundary.AirlineReservationSystem;

import java.sql.Connection;
import java.util.Map;

import Controller.AdminController;
//import Controller.UserController;
import Entity.Address;
import Entity.User;
import Entity.Name;
import Entity.Flight;
import Entity.Aircraft;

public class FlightReservation {
    public static void main(String[] args) {
        // Step 1: Create a DatabaseConnection instance
        DatabaseConnection db = DatabaseConnection.getOnlyInstance();
        Connection connection = db.getConnection();

        // Check if the database connection is successful
        if (connection != null) {
            // Step 2: Create a UserController instance
            UserController userController = new UserController(connection);

            // Step 3: Test the addUserToDB method with a new User
            User newUser = new User(); // This will use the default constructor to create a new user
            // Set other details for the user if needed
            newUser.getName().setFirstName("John");
            newUser.getName().setLastName("Doe");
            newUser.getAddress().setStreet("123 Main St");
            newUser.getAddress().setCity("Cityville");
            newUser.getAddress().setCountry("Countryland");
            newUser.setPhoneNumber(1234567890);
            newUser.setAccessLevel("customer");

            // Add the user to the database
            userController.addUserToDB(newUser);

            // Step 4: Test other UserController methods if needed
            // For example, you can test browseSeatMap or getUserAccessLevel

        } else {
            System.out.println("Failed to connect to the database. Check your connection details.");
        }
    }
}