package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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


public class AdminController extends User{
    
    private DatabaseConnection db;

    public AdminController() {
        this.db = DatabaseConnection.getOnlyInstance();
    }

    // Browse the list of flights, their origin and destination in a specific date.
    public Map<String, Flight> browseFlightsByDate (String date){
        Map<String, Flight> flightsMap = new HashMap<>();

        // Check if the date has the proper format "YYYY-MM-DD"
        if (!isValidDateFormat(date)) {
            System.out.println("Invalid date format. Please use 'YYYY-MM-DD'.");
            return flightsMap;
        }
        try (Connection connection = db.getConnection()) {
            // SQL query to retrieve flights based on departureDate
            String query = "SELECT * FROM FLIGHT WHERE departureDate = ?";

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
                        
                        // Create a Flight object and add it to the HashMap
                        Flight flight = new Flight(flightNumber, crewID,
                                new Location(destCity, destCountry),
                                new Location(originCity, originCountry),
                                capacity, departureDate, arrivalDate,
                                aircraft);

                        flightsMap.put(flightNumber, flight);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return flightsMap;
    }

    // Browse the list crews in a specific flight (for example flight number AB123 to New York).
    public void browseCrewByFlight(Flight flight){
        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()
        try (Connection connection = db.getConnection()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    // Browse the list Aircrafts that company owns
    public Map<Integer, Aircraft> browseAircrafts(){
        Map<Integer, Aircraft> aircraftMap = new HashMap<>();

        try (Connection connection = db.getConnection()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return aircraftMap;

    }

    public void addCrew(String pilotID, String copilotID, String fa1ID, String fa2ID, String fa3ID, String fa4ID){
        // Step 1: Generate a new crewID
        String crewID = generateNewCrewID();

        try (Connection connection = db.getConnection()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

    }

    public void removeCrew(String crewID){
        try (Connection connection = db.getConnection()) {
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
            }
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

        try (Connection connection = db.getConnection()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void removeAircraft(Aircraft aircraft){
        try (Connection connection = db.getConnection()) {
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
            }
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

            try (Connection connection = db.getConnection()) {
                // SQL query to insert a row into the DESTINATION table
                String query = "INSERT INTO DESTINATION (destinationCountry, destinationCity) VALUES (?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    // Set the values for the placeholders in the query
                    preparedStatement.setString(1, location.getCountry());
                    preparedStatement.setString(2, location.getCity());

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Location added to DESTINATION table successfully!");
                    } else {
                        System.out.println("Failed to add location to DESTINATION table.");
                    }
                }
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
            try (Connection connection = db.getConnection()) {
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
                }
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
        String flightQuery = "INSERT INTO FLIGHT (flightNumber, crewID, destination_Country, destination_city, origin_country, origin_city, capacity, departureDate, arrivalDate, aircraftID, aircraftModel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQL query to insert seat information into the SEAT table
        String seatQuery = "INSERT INTO SEAT (seatNumber, flightNumber, seatClass, isBooked, price, ticketNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = db.getConnection();
            PreparedStatement flightStatement = connection.prepareStatement(flightQuery);
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

            System.out.println("Flight information added successfully!");


            // Insert seat information for the flight
            for (int row = 1; row <= 20; row++) {
                for (char column : new char[]{'A', 'B', 'C'}) {
                    String seatNumber = String.format("%d%s", row, column);

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

            System.out.println("Seat information added successfully!");
        
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    public void removeFlightInfo(Flight flight){
        // Check if the passed Flight object is not null and has a flightNumber
        if (flight != null && flight.getFlightNumber() != null) {
            try (Connection connection = db.getConnection()) {
                // SQL query to delete a row from the FLIGHT table based on flightNumber
                String flightQuery = "DELETE FROM FLIGHT WHERE flightNumber = ?";

                // SQL query to delete rows from the SEAT table based on flightNumber
                String seatQuery = "DELETE FROM SEAT WHERE flightNumber = ?";
               
                PreparedStatement flightStatement = connection.prepareStatement(flightQuery);
                PreparedStatement seatStatement = connection.prepareStatement(seatQuery); 

                // Set the value for the placeholder in the flight query
                flightStatement.setString(1, flight.getFlightNumber());

                // Execute the flight query
                int flightRowsAffected = flightStatement.executeUpdate();

                if (flightRowsAffected > 0) {
                    System.out.println("Flight information removed successfully!");

                    // Set the value for the placeholder in the seat query
                    seatStatement.setString(1, flight.getFlightNumber());

                    // Execute the seat query
                    int seatRowsAffected = seatStatement.executeUpdate();

                    System.out.println(seatRowsAffected + " seats removed.");

                } else {
                    System.out.println("No flight with the specified flightNumber found.");
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
            try (Connection connection = db.getConnection()) {
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
                }
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

        try (Connection connection = db.getConnection()) {
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
        try (Connection connection = db.getConnection()) {
            String checkQuery = "SELECT COUNT(*) FROM AIRCRAFT WHERE aircraftID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, aircraftID);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
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

    // Method to check if a crewID already exists in the database
    private boolean crewIDExists(String crewID) {
        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()

        try (Connection connection = db.getConnection()) {
            // SQL query to check if the crewID exists in the CREW table
            String query = "SELECT COUNT(*) FROM CREW WHERE crewID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, crewID);

                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();

                // If the count is greater than 0, the crewID already exists
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Assume the crewID exists in case of an exception
        }
    }

    // Check if the destination already exists in the database
    private boolean destinationExists(String country, String city) {
        try (Connection connection = db.getConnection()) {
            String checkQuery = "SELECT COUNT(*) FROM DESTINATION WHERE destinationCountry = ? AND destinationCity = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, country);
                checkStatement.setString(2, city);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Return true to prevent insertion in case of an exception
        }
    }

    // Check if a flight with the given flightNumber already exists
    private boolean flightExists(String flightNumber) {
        try (Connection connection = db.getConnection()) {
            String checkQuery = "SELECT COUNT(*) FROM FLIGHT WHERE flightNumber = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, flightNumber);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return true; // Return true to prevent insertion in case of an exception
        }
    }

    private Aircraft getAircraftDetails(int aircraftID) {
        try (Connection connection = db.getConnection()) {
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
            }
            return aircraft;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        // Return a default Aircraft if not found
        return null;
    
        
    }


}
