package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Controller.DatabaseConnection;
import Entity.Address;
import Entity.Aircraft;
import Entity.Airline;
import Entity.Crew;
import Entity.Date;
import Entity.Flight;
import Entity.Location;
import Entity.Name;
import Entity.User;


public class AdminController extends Entity.User {
    
    private Connection connection;

    public AdminController(Connection connection) {
        this.connection = connection;
    }

    // Browse the list of flights, their origin and destination in a specific date.
    public Map<String, Flight> browseFlightsByDate (String date){
        Map<String, Flight> flightsMap = new HashMap<>();

        // Check if the date has the proper format "YYYY-MM-DD"
        if (!isValidDateFormat(date)) {
            System.out.println("Invalid date format. Please use 'YYYY-MM-DD'.");
            return flightsMap;
        }
        // try (Connection connection = db.getConnection()) {
            // SQL query to retrieve flights based on departureDate
            String query = "SELECT * FROM FLIGHTDB.FLIGHT WHERE departureDate = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set the value for the placeholder in the query
                preparedStatement.setString(1, date);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Process the result set
                    while (resultSet.next()) {
                        // Retrieve data from the result set and create Flight objects
                        String flightNumber = resultSet.getString("flightNumber");
                        String crewID = resultSet.getString("crewID");
                        String destCountry = resultSet.getString("destination_country");
                        String destCity = resultSet.getString("destination_city");
                        String originCountry = resultSet.getString("origin_country");
                        String originCity = resultSet.getString("origin_city");
                        int capacity = resultSet.getInt("capacity");
                        String departureDate = resultSet.getString("departureDate");
                        String arrivalDate = resultSet.getString("arrivalDate");
                        int aircraftID = resultSet.getInt("aircraftID");

                        // Retrieve additional details for the aircraft using the aircraftID
                        Aircraft aircraft = getAircraftDetails(aircraftID);
                        // Aircraft aircraft = new Aircraft(4, "7777", 90);
                        
                        // Create a Flight object and add it to the HashMap
                        Flight flight = new Flight(flightNumber, crewID,
                                new Location(destCity, destCountry),
                                new Location(originCity, originCountry),
                                capacity, departureDate, arrivalDate,
                                aircraft);

                        flightsMap.put(flightNumber, flight);
                    }
                }
        
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        } 

        return flightsMap;
    }

    // Browse the list crews in a specific flight (for example flight number AB123 to New York).
    // CHNAGE TO CREATE HASHMAP OF CREWID AND NAMES OF EMPLOYEES
    public Map<String, Crew[]> browseCrewByFlight(Flight flight){
        Map<String, Crew[]> crewMap = new HashMap<>();
        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()
        // try (Connection connection = db.getConnection()) {
            // SQL query to retrieve crew information for a specific flight
            String query = "SELECT c.crewID, c.pilot, c.copilot, c.flightAttendant1, c.flightAttendant2, c.flightAttendant3, c.flightAttendant4 " +
                        "FROM FLIGHT f " +
                        "JOIN CREW c ON f.crewID = c.crewID " +
                        "WHERE f.flightNumber = ?";

            printTable("FLIGHT");
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set the flight number parameter in the query
                preparedStatement.setString(1, flight.getFlightNumber());

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the result set
                while (resultSet.next()) {
                    String crewID = resultSet.getString("crewID");
                    int pilotID = resultSet.getInt("pilot");
                    int copilotID = resultSet.getInt("copilot");
                    int fa1ID = resultSet.getInt("flightAttendant1");
                    int fa2ID = resultSet.getInt("flightAttendant2");
                    int fa3ID = resultSet.getInt("flightAttendant3");
                    int fa4ID = resultSet.getInt("flightAttendant4");



                    Crew pilot = getCrewProfile(pilotID);
                    Crew copilot = getCrewProfile(copilotID);
                    Crew fa1 = getCrewProfile(fa1ID);
                    Crew fa2 = getCrewProfile(fa2ID);
                    Crew fa3 = getCrewProfile(fa3ID);
                    Crew fa4 = getCrewProfile(fa4ID);

                    Crew crewList[] = {pilot, copilot, fa1, fa2, fa3, fa4};

                    crewMap.put(crewID, crewList);
                    
                }
            printTable("CREW");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return crewMap;
    }

    // 
    // Browse the list Aircrafts that company owns
    public Map<Integer, Aircraft> browseAircrafts(){
        Map<Integer, Aircraft> aircraftMap = new HashMap<>();

        // try (Connection connection = db.getConnection()) {
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

    public void addCrew(int pilotID, int copilotID, int fa1ID, int fa2ID, int fa3ID, int fa4ID){
        // Step 1: Generate a new crewID
        String crewID = generateNewCrewID();

        // try (Connection connection = db.getConnection()) {
            // Step 2: Create an SQL INSERT statement
            String insertQuery = "INSERT INTO CREW (crewID, pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Step 3: Set parameters in the prepared statement
                preparedStatement.setString(1, crewID);
                preparedStatement.setInt(2, pilotID);
                preparedStatement.setInt(3, copilotID);
                preparedStatement.setInt(4, fa1ID);
                preparedStatement.setInt(5, fa2ID);
                preparedStatement.setInt(6, fa3ID);
                preparedStatement.setInt(7, fa4ID);

                // Execute the INSERT statement
                preparedStatement.executeUpdate();

                System.out.println("Crew added successfully with crewID: " + crewID);
                printTable("CREW");
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

    }

    public void removeCrew(String crewID){
        // try (Connection connection = db.getConnection()) {
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

                printTable("CREW");
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void addAircraft(Aircraft aircraft){
        // Check if the aircraftID already exists
        if (aircraftExists(aircraft.getAircraftID())) {
            System.out.println("Aircraft with ID " + aircraft.getAircraftID() + " already exists in the database.");
            return;
        }

        // try (Connection connection = db.getConnection()) {
            // SQL query to insert an aircraft into the AIRCRAFT table
            String insertQuery = "INSERT INTO AIRCRAFT (aircraftID, aircraftModel, capacity) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set parameters in the prepared statement
                preparedStatement.setInt(1, aircraft.getAircraftID());
                preparedStatement.setString(2, aircraft.getModel());
                preparedStatement.setInt(3, aircraft.getCapacity());

                // Execute the INSERT statement
                preparedStatement.executeUpdate(); // executeAdd

                System.out.println("Aircraft added successfully with ID: " + aircraft.getAircraftID());

                printTable("AIRCRAFT");
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void removeAircraft(Aircraft aircraft){
        // try (Connection connection = db.getConnection()) {
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
                printTable("AIRCRAFT");
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void addDestination(Location location){
        // Check if the passed Location object is not null
        if (location != null) {

            // Check if the destination already exists
            if (destinationExists(location.getCountry(), location.getCity())) {
                System.out.println("Destination already exists in the DESTINATION table.");
                return;
            }

            int newDestinationID = generateNewDestinationID();

            // CREATE UNIQUE DESTINATION ID

            // try (Connection connection = db.getConnection()) {
                // SQL query to insert a row into the DESTINATION table
                String query = "INSERT INTO DESTINATION (destinationID, destinationCountry, destinationCity) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    // Set the values for the placeholders in the query
                    preparedStatement.setInt(1, newDestinationID);
                    preparedStatement.setString(2, location.getCountry());
                    preparedStatement.setString(3, location.getCity());

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Location added to DESTINATION table successfully!");
                    } else {
                        System.out.println("Failed to add location to DESTINATION table.");
                    }
                
                    printTable("DESTINATION");
                
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions as needed
            }
        } else {
            System.out.println("Invalid Location object.");
        }
    }

    public void removeDestination(Location location){
        // Check if the passed Location object is not null
        if (location != null) {
            // try (Connection connection = db.getConnection()) {
                // SQL query to delete a row from the DESTINATION table
                String query = "DELETE FROM DESTINATION WHERE destinationCountry = ? AND destinationCity = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    // Set the values for the placeholders in the query
                    preparedStatement.setString(1, location.getCountry());
                    preparedStatement.setString(2, location.getCity());

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Location removed from DESTINATION table successfully!");
                    } else {
                        System.out.println("Location not found in DESTINATION table.");
                    }
                    printTable("DESTINATION");
                
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions as needed
            }
        } else {
            System.out.println("Invalid Location object.");
        }
    }

    // Add flight information to the FLIGHT table
    public void addFlightInfo(Flight flight) {

        // Check if the flightNumber already exists
        if (flightExists(flight.getFlightNumber())) {
            System.out.println("Flight with the same flightNumber already exists.");
            return;
        }

        
        // SQL query to insert flight information into the FLIGHT table
        String flightQuery = "INSERT INTO FLIGHTDB.FLIGHT (flightNumber, crewID, destination_Country, destination_city, origin_country, origin_city, capacity, departureDate, arrivalDate, aircraftID, aircraftModel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQL query to insert seat information into the SEAT table
        String seatQuery = "INSERT INTO SEAT (seatNumber, flightNumber, seatClass, isBooked) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement flightStatement = connection.prepareStatement(flightQuery);
            PreparedStatement seatStatement = connection.prepareStatement(seatQuery)) {

            // Set values for the placeholders in the flight query
            flightStatement.setString(1, flight.getFlightNumber());
            flightStatement.setString(2, flight.getCrewID());
            flightStatement.setString(3, flight.getDestination().getCountry());
            flightStatement.setString(4, flight.getDestination().getCity());
            flightStatement.setString(5, flight.getOrigin().getCountry());
            flightStatement.setString(6, flight.getOrigin().getCity());
            flightStatement.setInt(7, flight.getCapacity());
            flightStatement.setString(8, flight.getDepartureDate());
            flightStatement.setString(9, flight.getArrivalDate());
            flightStatement.setInt(10, flight.getAircraft().getAircraftID());
            flightStatement.setString(11, flight.getAircraft().getAircraftModel());

            // Execute the flight query
            flightStatement.executeUpdate();

            printTable("FLIGHT");
            System.out.println("Flight information added successfully!");


            // Insert seat information for the flight
            for (int row = 1; row <= 20; row++) {
                for (char column : new char[]{'A', 'B', 'C'}) {
                    String seatNumber = String.format("%d%s", row, column);
                    //System.out.println(seatNumber);

                    // Set values for the placeholders in the seat query
                    seatStatement.setString(1, seatNumber);
                    seatStatement.setString(2, flight.getFlightNumber());

                    if (row >= 1 && row <=5) {
                        seatStatement.setString(3, "First Class");
                    }
                    else if (row >= 6 && row <=13) {
                        seatStatement.setString(3, "Business Class");
                    }
                    else if (row >= 14 && row <=20) {
                        seatStatement.setString(3, "Economy Class");
                    }
                    // need to get seat class associated with each seat
                    seatStatement.setBoolean(4, false);  // Initialize as not booked

                    // Execute the seat query
                    seatStatement.executeUpdate();
                }
            }

            
            //printTable("SEAT");

            System.out.println("Seat information added successfully!");
        
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void removeFlightInfo(Flight flight) {
        // Check if the passed Flight object is not null and has a flightNumber
        if (flight != null && flight.getFlightNumber() != null) {
            try {
                // Delete TICKET records associated with the flight
                String ticketQuery = "DELETE FROM TICKET WHERE flightNumber = ?";
                try (PreparedStatement ticketStatement = connection.prepareStatement(ticketQuery)) {
                    ticketStatement.setString(1, flight.getFlightNumber());
                    int ticketRowsAffected = ticketStatement.executeUpdate();
                    System.out.println(ticketRowsAffected + " tickets removed.");
                }
    
                // Delete SEAT records associated with the flight
                String seatQuery = "DELETE FROM SEAT WHERE flightNumber = ?";
                try (PreparedStatement seatStatement = connection.prepareStatement(seatQuery)) {
                    seatStatement.setString(1, flight.getFlightNumber());
                    int seatRowsAffected = seatStatement.executeUpdate();
                    System.out.println(seatRowsAffected + " seats removed.");
                }
    
                // Delete FLIGHT record
                String flightQuery = "DELETE FROM FLIGHT WHERE flightNumber = ?";
                try (PreparedStatement flightStatement = connection.prepareStatement(flightQuery)) {
                    flightStatement.setString(1, flight.getFlightNumber());
                    int flightRowsAffected = flightStatement.executeUpdate();
    
                    if (flightRowsAffected > 0) {
                        System.out.println("Flight information removed successfully!");
                        printTable("FLIGHT");
                    } else {
                        System.out.println("No flight with the specified flightNumber found.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions as needed
            }
        } else {
            System.out.println("Invalid Flight object or flightNumber.");
        }
    }
    
    
    

    public void modifyFlightInfo(Flight flight){
        // CHANGE IF FLIGHT NUMBER MODFIED THEN CHANGE SEATS FLIGHTNUMBER
        // Check if the passed Flight object is not null
        if (flight != null) {
            // try (Connection connection = db.getConnection()) {
                // SQL query to update a row in the FLIGHT table based on flightNumber
                String query = "UPDATE FLIGHT SET crewID = ?, destination_country = ?, destination_city = ?, " +
                            "origin_country = ?, origin_city = ?, capacity = ?, departureDate = ?, " +
                            "arrivalDate = ?, aircraftID = ?, aircraftModel = ? WHERE flightNumber = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    // Set the values for the placeholders in the query
                    preparedStatement.setString(1, flight.getCrewID());
                    preparedStatement.setString(2, flight.getDestination().getCountry());
                    preparedStatement.setString(3, flight.getDestination().getCity());
                    preparedStatement.setString(4, flight.getOrigin().getCountry());
                    preparedStatement.setString(5, flight.getOrigin().getCity());
                    preparedStatement.setInt(6, flight.getCapacity());
                    preparedStatement.setString(7, flight.getDepartureDate());
                    preparedStatement.setString(8, flight.getArrivalDate());
                    preparedStatement.setInt(9, flight.getAircraft().getAircraftID());
                    preparedStatement.setString(10, flight.getAircraft().getModel());
                    preparedStatement.setString(11, flight.getFlightNumber());

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Flight information modified successfully!");
                    } else {
                        System.out.println("Flight not found in the FLIGHT table.");
                    }
                
                    printTable("FLIGHT");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions as needed
            }
        } else {
            System.out.println("Invalid Flight object.");
        }
    }

    // Print list of users who have registered with the airline company.
    public Map<Integer, User> viewRegisteredUsers(){
        Map<Integer, User> registeredUsersMap = new HashMap<>();

        // try (Connection connection = db.getConnection()) {
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
                    User user = new User(userID, true, name, address, phoneNumber, email, pass, accessLevel);

                    // Add the User object to the HashMap
                    registeredUsersMap.put(userID, user);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return registeredUsersMap;
    }

    // Check if the date has the format "YYYY-MM-DD"
    private boolean isValidDateFormat(String date) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(regex);
    }

    // Check if the aircraftID already exists in the database
    private boolean aircraftExists(int aircraftID) {
        // try (Connection connection = db.getConnection()) {
            String checkQuery = "SELECT COUNT(*) FROM AIRCRAFT WHERE aircraftID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, aircraftID);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Return true to prevent insertion in case of an exception
        }
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

    // Method to generate a new 4-digit unique destinationID
    private int generateNewDestinationID() {
        int newDestinationID;

        // Generate a random 4-digit number
        do {
            newDestinationID = 1000 + new Random().nextInt(9000);
        } while (destinationIDExists(newDestinationID));

        return newDestinationID;
    }

    // Method to check if a destinationID already exists in the database
    private boolean destinationIDExists(int destinationID) {
        // try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // SQL query to check if the destinationID exists in the DESTINATION table
            String query = "SELECT COUNT(*) FROM DESTINATION WHERE destinationID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, destinationID);

                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();

                // If the count is greater than 0, the destinationID already exists
                return resultSet.getInt(1) > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions as needed
                return true; // Assume the destinationID exists in case of an exception
            }
        
    }


    // Method to check if a crewID already exists in the database
    private boolean crewIDExists(String crewID) {
        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()

        // try (Connection connection = db.getConnection()) {
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

    // Check if the destination already exists in the database
    private boolean destinationExists(String country, String city) {
        // try (Connection connection = db.getConnection()) {
            String checkQuery = "SELECT COUNT(*) FROM DESTINATION WHERE destinationCountry = ? AND destinationCity = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, country);
                checkStatement.setString(2, city);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Return true to prevent insertion in case of an exception
        }
    }

    // Check if a flight with the given flightNumber already exists
    private boolean flightExists(String flightNumber) {
        // try (Connection connection = db.getConnection()) {
            String checkQuery = "SELECT COUNT(*) FROM FLIGHT WHERE flightNumber = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, flightNumber);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Return true to prevent insertion in case of an exception
        }
    }

    private Aircraft getAircraftDetails(int aircraftID) {
        // try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM AIRCRAFT WHERE aircraftID = ?";
            Aircraft aircraft = null;
        
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, aircraftID);
        
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String aircraftModel = resultSet.getString("aircraftModel");
                        int capacity = resultSet.getInt("capacity");
        
                        aircraft = new Aircraft(aircraftID, aircraftModel, capacity);
                        
                    }
                }
            
            return aircraft;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        // Return a default Aircraft if not found
        return null;
    
        
    }

    public void printTable(String tableName) {
            String query = "SELECT * FROM " + tableName;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Get column names
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", resultSet.getMetaData().getColumnName(i));
                }
                System.out.println();

                // Print table data
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-20s", resultSet.getString(i));
                    }
                    System.out.println();
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Crew getCrewProfile(int userID) {
        Crew crew = null;

        // SQL query to retrieve user information based on userID
        String query = "SELECT firstName, lastName, accessLevel FROM USERS WHERE userID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String accessLevel = resultSet.getString("accessLevel");

                    // Create a Crew object with the retrieved information
                    crew = new Crew(firstName + " " + lastName, accessLevel, String.valueOf(userID));
                } else {
                    System.out.println("User not found with userID: " + userID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return crew;
    }


}
