import Controller.AdminController;
import Controller.AirlineUser;
import Controller.DatabaseConnection;
import Controller.UserController;


import javax.swing.*;
import Boundary.AirlineReservationSystem;
import java.sql.*;
import java.util.List;
import java.util.Map;

import Entity.Address;
import Entity.User;
import Entity.Name;
import Entity.Flight;
import Entity.Aircraft;
import Entity.Crew;
import Entity.Location;

public class FlightReservation {
    public static void main(String[] args) {
        // Step 1: Create a DatabaseConnection instance
        DatabaseConnection db = DatabaseConnection.getOnlyInstance();
        Connection connection = db.getConnection();
        UserController usc = new UserController();
        AdminController adminController = new AdminController();

        // PASS connection TO ADMINCONTROLLER AND AIRLINEUSER TO USE
        if (connection != null) {
            
            System.out.println("Connection to the database is successful!");
            SwingUtilities.invokeLater(() -> {
                AirlineReservationSystem frame = new  AirlineReservationSystem(usc,adminController);
                
                frame.setVisible(true);
            });

        } else {
            System.out.println("Failed to connect to the database. Check your connection details.");
        }
    }
}


// import controller.DatabaseConnection;
// import Controller.UserController;
// import Entity.Address;
// import Entity.User;
// import Entity.Name;

// public class FlightReservation {
//     public static void main(String[] args) {
//         // Step 1: Create a DatabaseConnection instance
//         //EVERYONE: use the following like to send db to the controller classes
//         DatabaseConnection db = DatabaseConnection.getOnlyInstance();

//         // Check if the database connection is successful
//         if (dbConnection.getConnection() != null) {
//             System.out.println("Connection to the database is successful!");

//             // Step 2: Create a UserController instance using the DatabaseConnection
//             UserController userController = new UserController(dbConnection);

//             // Step 3: Create a sample User object
//             Name sampleName = new Name("John", "Doe"); // Replace with actual names
//             Address sampleAddress = new Address("123 Main St", "Cityville", "Countryland"); // Replace with actual address
//             int samplePhoneNumber = 1234567890; // Replace with actual phone number
//             String sampleEmail = "john.doe@example.com"; // Replace with actual email
//             String samplePassword = "password123"; // Replace with actual password
//             String sampleAccessLevel = "Customer"; // Replace with actual access level

//             User sampleUser = new User(true, sampleName, sampleAddress, samplePhoneNumber, sampleEmail, samplePassword, sampleAccessLevel);

//             // Call the addUserToDB method
//             userController.addUserToDB(sampleUser);
//         } else {
//             System.out.println("Failed to connect to the database. Check your connection details.");
//         }
//     }
// }


 