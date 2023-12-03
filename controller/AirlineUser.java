package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entity.Flight;
import Entity.User;

public class AirlineUser {
    
    private Connection connection;

    public AirlineUser() {
        this.connection = connection;
    }
    
    public Map<String, List<String>> browsePassengers(String flightNum) {
        Map<String, List<String>> passengerMap = new HashMap<>();

        // Assuming you have a valid Connection object named "connection" from DatabaseConnection.getConnection()
        // try (Connection connection = db.getConnection()) {
        // SQL query to retrieve ticket information for a specific flight
        String query = "SELECT * FROM TICKET WHERE flightNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the flight number parameter in the query
            preparedStatement.setString(1, flightNum);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Retrieve ticket information
                String ticketNumber = resultSet.getString("ticketNumber");
                String passengerFirstName = resultSet.getString("passenger_fName");
                String passengerLastName = resultSet.getString("passenger_lName");
                String seatNumber = resultSet.getString("seatNumber");
                String seatClass = resultSet.getString("seatClass");
                int userID = resultSet.getInt("userID");

                // Create a list of passenger details
                List<String> passengerDetails = Arrays.asList(
                        ticketNumber, passengerFirstName, passengerLastName, seatNumber, seatClass, String.valueOf(userID));

                // Add the passenger details to the HashMap using ticketNumber as the key
                passengerMap.put(ticketNumber, passengerDetails);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return passengerMap;
    }

    


}