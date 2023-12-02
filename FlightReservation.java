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

        // PASS connection TO ADMINCONTROLLER AND AIRLINEUSER TO USE
        if (connection != null) {
            
            // System.out.println("Connection to the database is successful!");
            // SwingUtilities.invokeLater(() -> {
            //     AirlineReservationSystem frame = new AirlineReservationSystem(usc);
                
            //     frame.setVisible(true);
            // });
            // Variables
            Location origin = new Location("Calgary", "Canada");
            Location location = new Location("Karachi", "Pakistan");
            Aircraft check = new Aircraft(5678, "A380", 200);
            Flight flight2 = new Flight("FL012", "CR003", location, origin, 200, "2023-06-05", "2023-06-06", check);
            Aircraft aircraft2 = new Aircraft(2890, "B777", 220);
            String testDate = "2023-12-01";

            // Creating Connection
            AdminController adminController = new AdminController(connection);
            AirlineUser airlineUser = new AirlineUser(connection);

            // Hashmaps 
            Map<String, Flight> flightsMap = adminController.browseFlightsByDate(testDate);
            Map<String, Crew[]> crewsMap = adminController.browseCrewByFlight("FL001");
            Map<Integer, Aircraft> aircraftMap = adminController.browseAircrafts();
            Map<Integer, User> registeredUsersMap = adminController.viewRegisteredUsers();
            Map<String, List<String>> passengersMap = airlineUser.browsePassengers("FL001");

            // Print HASHMAP Results
            System.out.println("Flights on " + testDate + ":");
            for (Map.Entry<String, Flight> entry : flightsMap.entrySet()) {
                String flightNumber = entry.getKey();
                Flight flight = entry.getValue();
            
                System.out.println("Flight Number: " + flightNumber);
                System.out.println("Flight Details: " + flight);
                System.out.println(); // Add a newline for better readability
            }

            System.out.println("Crew by Flight:");
            for (Map.Entry<String, Crew[]> entry : crewsMap.entrySet()) {
                String crewID = entry.getKey();
                Crew[] crewArray = entry.getValue();

                System.out.println("crewID: " + crewID);

                // Iterate through the array of Crew objects
                for (Crew crew : crewArray) {
                    // Assuming Crew class has a toString method
                    System.out.println("Crew Details: " + crew);
                }

                System.out.println(); // Add a newline for better readability
            }

            System.out.println("Aircrafts:");
            for (Map.Entry<Integer, Aircraft> entry : aircraftMap.entrySet()) {
                Integer aircraftID = entry.getKey();
                Aircraft aircraft = entry.getValue();
            
                System.out.println("AircraftID: " + aircraftID);
                System.out.println("Aircraft Details: " + aircraft);
                System.out.println(); // Add a newline for better readability
            }

            System.out.println("Registered Users:");
            for (Map.Entry<Integer, User> entry : registeredUsersMap.entrySet()) {
                Integer userID = entry.getKey();
                User user = entry.getValue();
            
                System.out.println("userID: " + userID);
                System.out.println("Regsitered User Details: " + user);
                System.out.println(); // Add a newline for better readability
            }

            // Call Database Functions
        
            adminController.addCrew(11, 13, 15, 16, 17, 18);
            adminController.removeCrew("CR005");

            adminController.addAircraft(aircraft2);
            adminController.removeAircraft(2890);

            adminController.addDestination(location);
            adminController.removeDestination(7890);
            
            adminController.addFlightInfo(flight2);
            adminController.removeFlightInfo("FL001");
            Flight flight3 = new Flight("FL012", "CR004", origin, location, 200, "2023-12-05", "2023-12-06", check);

            adminController.modifyFlightInfo(flight3);

            System.out.println("Passengers:");
            for (Map.Entry<String, List<String>> entry : passengersMap.entrySet()) {
                String ticketNum = entry.getKey();
                List<String> passengers = entry.getValue();

                System.out.println("Ticket Number: " + ticketNum);
                
                // Print passenger details individually
                System.out.println("Passenger Details:");
                for (String detail : passengers) {
                    System.out.println("  " + detail);
                }

                System.out.println(); // Add a newline for better readability
            }

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


 