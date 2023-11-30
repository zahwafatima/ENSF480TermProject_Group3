

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

            // add tests here 
        } else {
            System.out.println("Failed to connect to the database. Check your connection details.");
        }
    }
}