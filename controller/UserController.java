package Controller;


//import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import Controller.DatabaseConnection;
import Entity.*;


public class UserController {

    //private Connection dbConnection;

    public UserController() {
        
    }
    
    //add a new user to the database 
    public void addUserToDB(User user) {
        String sql = "INSERT INTO FLIGHTDB.USERS (userID, isRegistered, firstName, lastName, street, city, country, email, pass, phoneNumber, accessLevel) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if(DatabaseConnection.dbConnect!=null){
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getUserID()); 
            preparedStatement.setBoolean(2, user.getIsRegistered()); 
            preparedStatement.setString(3, user.getName().getFirstName());
            preparedStatement.setString(4, user.getName().getLastName());
            preparedStatement.setString(5, user.getAddress().getStreet());
            preparedStatement.setString(6, user.getAddress().getCity());
            preparedStatement.setString(7, user.getAddress().getCountry());
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.setString(9, user.getPass());
            preparedStatement.setString(10, String.valueOf(user.getPhoneNumber()));
            preparedStatement.setString(11, ("customer")); 

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added to the database successfully.");
            } else {
                System.out.println("Failed to add user to the database.");
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }}
        else{

        }
    }
    // Browse all flights in the table, regardless of the date.
    public Map<String, Flight> browseAllFlights() {
        Map<String, Flight> flightsMap = new HashMap<>();

        String query = "SELECT * FROM FLIGHTDB.FLIGHT";

        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
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
                String aircraftModel = resultSet.getString("aircraftModel");

                // Create a Flight object and add it to the HashMap
                Flight flight = new Flight(flightNumber, crewID,
                        new Location(destCity, destCountry),
                        new Location(originCity, originCountry),
                        capacity, departureDate, arrivalDate,
                        new Aircraft(aircraftID, aircraftModel, capacity));

                flightsMap.put(flightNumber, flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving flights from the database: " + e.getMessage());
        }

        return flightsMap;
    }

    // user has chosen a flight from GUI after seeing flights list from browseAllFlights, flightNum is passed
    public void selectFlight(String flightNum) {
        // show user the current seat map for this flight, 
        // GUI should show which are available and not based on isBooked attribute
        browseSeatMap(flightNum);
        // the user should pick input a seat after seeing the seatMap, passing this value to selectSeat
    }

    // user has seen seatMap and chosen a seat, this function takes that seatNumber and changes isBooked to true in the SEAT table, 
    // and creates a ticket for them associated with their ID, name, etc.
    public void selectSeat(String seatNum, String flightNum, int userID) {
        // change seat row isBooked to true
        // SQL query to update the isBooked attribute to TRUE based on seatNumber and flightNumber
        String updateQuery = "UPDATE SEAT SET isBooked = TRUE WHERE seatNumber = ? AND flightNumber = ?";

        try (PreparedStatement updateStatement = DatabaseConnection.dbConnect.prepareStatement(updateQuery)) {
            // Set values for the placeholders in the update query
            updateStatement.setString(1, seatNum);
            updateStatement.setString(2, flightNum);

            // Execute the update query
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Seat " + seatNum + " on Flight " + flightNum + " booked successfully!");
            } else {
                System.out.println("No matching seat found for the specified seatNumber and flightNumber.");
            }

            // create Ticket Number
            // already have flightNum
            // get first and last name from userID
            
            // already have seatNum
            // get seatClass
            // already have userID


        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

    }



    //returns a map of all of the seats associated with an aircraft 
    public Map<String, Seat> browseSeatMap(String flightNum) {
        Map<String, Seat> seatMap = new HashMap<>();

        String sql = "SELECT * FROM FLIGHTDB.SEATS WHERE flightNumber = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            // Set the flightNum parameter before executing the query
            preparedStatement.setString(1, flightNum);

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatMap;
    }

    


    public Map<String, User> getUserDetails(String email, String password) {
        Map<String, User> userDetails = new HashMap<>();

        String sql = "SELECT * FROM USERS WHERE email = ? AND pass = ?";
        
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // User found, create a map with user details
                    User user = new User(
                        resultSet.getInt("userID"),
                        resultSet.getBoolean("isRegistered"),
                        new Name(resultSet.getString("firstName"), resultSet.getString("lastName")),
                        new Address(resultSet.getString("street"), resultSet.getString("city"), resultSet.getString("country")),
                        resultSet.getLong("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("pass"),
                        resultSet.getString("accessLevel")
                    );
                    userDetails.put("user", user);

                    return userDetails;
                } else {
                    // No user found with the given email and password
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    //find accessLevel after login 
    public String getUserAccessLevel(String email, String password) {

        String sql = "SELECT accessLevel FROM USERS WHERE email = ? AND pass = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
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
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
    return "Error retrieving user access level";
    }

    public ArrayList<String> emailList() {
        ArrayList<String> emails = new ArrayList<>();
        String sql = "SELECT email FROM FLIGHTDB.USERS";
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    emails.add(email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, log it, or throw a custom exception
        }
        return emails;
    }

    public ArrayList<String> passwordList() {
        ArrayList<String> passwords = new ArrayList<>();
        String sql = "SELECT pass FROM FLIGHTDB.USERS";
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String password = resultSet.getString("pass");
                    passwords.add(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, log it, or throw a custom exception
        }
        return passwords;
    }

    public void makePayment(User user, Flight flight, Ticket ticket) {
        // Check user type and calculate payment
        int payment = user.calculatePayment(ticket);

        // Create a receipt with user, flight, and ticket details
        
        String receipt = String.format("Receipt for %s %s%nFlight: %s%nSeat: %s%nPrice: $%d",
                user.getName().getFirstName(), user.getName().getLastName(),
                flight.getFlightNumber(), ticket.getSeat().getSeatNumber(), payment);

        // Download the receipt as a file
        downloadReceiptFile(receipt);
    }

    private void downloadReceiptFile(String receipt) {
    // download the receipt as a receipt file
        // try (CSVWriter writer = new CSVWriter(new FileWriter("receipt.csv"))) {
            // // Split the receipt string into lines
            // String[] lines = receipt.split("\\n");

            // // Write each line to the CSV file
            // for (String line : lines) {
            //     // Split each line into columns (assuming a comma as a separator)
            //     String[] columns = line.split(",");

            //     // Write the columns to the CSV file
            //     writer.writeNext(columns);
            // }

            System.out.println("Receipt successfully written to receipt.csv");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
    
}
