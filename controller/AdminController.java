package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Entity.Address;
import Entity.Aircraft;
import Entity.Airline;
import Entity.Crew;
import Entity.Date;
import Entity.Flight;
import Entity.Location;
import Entity.Name; 
import Entity.User;
import DatabaseConnection;

public class AdminController {
    
    // Browse the list of flights, their origin and destination in a specific date.
    public Map<String, Flight> browse_flights_spec(Date date){
        Map<String, Flight> flightsMap = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM FLIGHT WHERE departureDate = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                Date sqlDate = new java.sql.Date(date.getYear(), date.getMonth(), date.getDay());
                preparedStatement.setDate(1, sqlDate);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Flight flight = createFlightFromResultSet(resultSet);
                        flightsMap.put(flight.getFlightNumber(), flight);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flightsMap;
    }

    // Browse the list crews in a specific flight (for example flight number AB123 to New York).
    public void browse_crew_spec(Flight flight){
        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()

        // SQL query to retrieve crew information for a specific flight
        String query = "SELECT c.crewID, c.pilot, c.copilot, c.flightAttendant1, c.flightAttendant2, c.flightAttendant3, c.flightAttendant4 " +
                       "FROM FLIGHT f " +
                       "JOIN CREW c ON f.crewID = c.crewID " +
                       "WHERE f.flightNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the flight number parameter in the query
            preparedStatement.setString(1, flight.getFlightNumber());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int crewID = resultSet.getInt("crewID");
                int pilotID = resultSet.getInt("pilot");
                int copilotID = resultSet.getInt("copilot");
                int fa1ID = resultSet.getInt("flightAttendant1");
                int fa2ID = resultSet.getInt("flightAttendant2");
                int fa3ID = resultSet.getInt("flightAttendant3");
                int fa4ID = resultSet.getInt("flightAttendant4");

                // Do something with the crew information (e.g., print or store in a data structure)
                System.out.println("CrewID: " + crewID);
                System.out.println("PilotID: " + pilotID);
                System.out.println("CoPilotID: " + copilotID);
                System.out.println("FlightAttendant1ID: " + fa1ID);
                System.out.println("FlightAttendant2ID: " + fa2ID);
                System.out.println("FlightAttendant3ID: " + fa3ID);
                System.out.println("FlightAttendant4ID: " + fa4ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    // Browse the list Aircrafts that company owns
    public Map<Integer, Aircraft> browse_aircrafts(){
        Map<Integer, Aircraft> aircraftMap = new HashMap<>();

        // SQL query to retrieve all aircraft data from the AIRCRAFT table
        String query = "SELECT aircraftID, aircraftModel, capacity FROM AIRCRAFT";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int aircraftID = resultSet.getInt("aircraftID");
                String model = resultSet.getString("aircraftModel");
                int capacity = resultSet.getInt("capacity");

                // Create an Aircraft object
                Aircraft aircraft = new Aircraft(aircraftID, model, capacity);

                // Add the Aircraft object to the HashMap
                aircraftMap.put(aircraftID, aircraft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return aircraftMap;

    }

    public void add_crew(String pilotID, String copilotID, String fa1ID, String fa2ID, String fa3ID, String fa4ID){
        // Step 1: Generate a new crewID
        String crewID = generateNewCrewID();

        // Step 2: Create an SQL INSERT statement
        String insertQuery = "INSERT INTO CREW (crewID, pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Step 3: Set parameters in the prepared statement
            preparedStatement.setString(1, crewID);
            preparedStatement.setString(2, pilotID);
            preparedStatement.setString(3, copilotID);
            preparedStatement.setString(4, fa1ID);
            preparedStatement.setString(5, fa2ID);
            preparedStatement.setString(6, fa3ID);
            preparedStatement.setString(7, fa4ID);

            // Execute the INSERT statement
            preparedStatement.executeUpdate();

            System.out.println("Crew added successfully with crewID: " + crewID);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

    }

    public void remove_crew(String crewID){
        // SQL query to remove the crew based on crewID
        String removeQuery = "DELETE FROM CREW WHERE crewID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(removeQuery)) {
            // Set the crewID parameter in the prepared statement
            preparedStatement.setString(1, crewID);

            // Execute the DELETE statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Crew with crewID " + crewID + " removed successfully.");
            } else {
                System.out.println("No crew found with crewID " + crewID + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void add_aircraft(Aircraft aircraft){
        // SQL query to insert an aircraft into the AIRCRAFT table
        String insertQuery = "INSERT INTO AIRCRAFT (aircraftID, aircraftModel, capacity) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Set parameters in the prepared statement
            preparedStatement.setInt(1, aircraft.getAircraftID());
            preparedStatement.setString(2, aircraft.getModel());
            preparedStatement.setInt(3, aircraft.getCapacity());

            // Execute the INSERT statement
            preparedStatement.executeUpdate();

            System.out.println("Aircraft added successfully with ID: " + aircraft.getAircraftID());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void remove_aircraft(Aircraft aircraft){
        // SQL query to remove an aircraft from the AIRCRAFT table based on aircraftID
        String deleteQuery = "DELETE FROM AIRCRAFT WHERE aircraftID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            // Set the aircraftID parameter in the prepared statement
            preparedStatement.setInt(1, aircraft.getAircraftID());

            // Execute the DELETE statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Aircraft with ID " + aircraft.getAircraftID() + " removed successfully.");
            } else {
                System.out.println("No aircraft found with ID " + aircraft.getAircraftID() + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void add_destination(){

    }

    public void remove_destination(){

    }

    public void add_flight_info(){

    }

    public void remove_flight_info(){

    }

    public void modify_flight_info(){

    }

    // Print list of users who have registered with the airline company.
    public Map<Integer, User> view_registered_users(){
        Map<Integer, User> registeredUsersMap = new HashMap<>();

        // SQL query to retrieve registered users from the USERS table
        String query = "SELECT userID, firstName, lastName, street, city, country, email, pass, phoneNumber, accessLevel FROM USERS WHERE isRegistered = true";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int userID = resultSet.getInt("userID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                long phoneNumber = resultSet.getLong("phoneNumber");
                String accessLevel = resultSet.getString("accessLevel");

                Name name = new Name(firstName, lastName);
                Address address = new Address(street, city, country);
                // Create a User object
                User user = new User(true, name, address, phoneNumber, email, pass, accessLevel);

                // Add the User object to the HashMap
                registeredUsersMap.put(userID, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return registeredUsersMap;
    }

    // Helper method to create a Flight object from a ResultSet
    private Flight createFlightFromResultSet(ResultSet resultSet) throws SQLException {
        // Extract data from ResultSet and create a Flight object
        // You might need to modify this based on your actual table structure
        // For simplicity, assuming you have methods to get Airline, Aircraft, Location objects
        String flightNumber = resultSet.getString("flightNumber");
        Airline airline = getAirlineFromResultSet(resultSet);
        Aircraft aircraft = getAircraftFromResultSet(resultSet);
        Location destination = getLocationFromResultSet(resultSet, "destination_Country", "destination_city");
        Location origin = getLocationFromResultSet(resultSet, "origin_country", "origin_city");
        int seatCapacity = resultSet.getInt("capacity");
        String departureTime = resultSet.getString("departureDate");
        String arrivalTime = resultSet.getString("arrivalDate");

        return new Flight(flightNumber, airline, destination, origin, seatCapacity, departureTime, arrivalTime);
    }

    // Method to generate a new unique crewID (CR + random 3-digit number)
    private String generateNewCrewID() {
        String newCrewID;
        
        // Generate a random 3-digit number
        int randomNum = (int) (Math.random() * 900) + 100;

        // Concatenate "CR" with the random number
        newCrewID = "CR" + randomNum;

        // Check if the generated crewID already exists in the database
        while (crewIDExists(newCrewID)) {
            // Regenerate a new crewID until it is unique
            randomNum = (int) (Math.random() * 900) + 100;
            newCrewID = "CR" + randomNum;
        }

        return newCrewID;
    }

    // Method to check if a crewID already exists in the database
    private boolean crewIDExists(String crewID) {
        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()

        // SQL query to check if the crewID exists in the CREW table
        String query = "SELECT COUNT(*) FROM CREW WHERE crewID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, crewID);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            // If the count is greater than 0, the crewID already exists
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Assume the crewID exists in case of an exception
        }
    }


}
