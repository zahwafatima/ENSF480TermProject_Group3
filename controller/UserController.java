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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import Controller.DatabaseConnection;
import Controller.GuestUser;
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
        // GUI should show which seats are available and not based on isBooked attribute
        browseSeatMap(flightNum);
        // the user should pick input a seat after seeing the seatMap, passing this value to selectSeat
    }

    public void makePayment(String seatNum, String flightNum, int userID) {
   
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // user has seen seatMap and chosen a seat, this function takes that seatNumber and changes isBooked to true in the SEAT table, 
    // and creates a ticket for them associated with their ID, name, etc.


    // user has seen seatMap and chosen a seat, this function takes that seatNumber and changes isBooked to true in the SEAT table, 
    // and creates a ticket for them associated with their ID, name, etc.
    public void generateTicket(String seatNum, String flightNum, int userID) {
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

            // Create Ticket Number
            String ticketNumber = generateUniqueTicketNumber();

            // Get user information from USERS table
            String userQuery = "SELECT firstName, lastName FROM USERS WHERE userID = ?";
            try (PreparedStatement userStatement = DatabaseConnection.dbConnect.prepareStatement(userQuery)) {
                userStatement.setInt(1, userID);
                ResultSet userResultSet = userStatement.executeQuery();

                if (userResultSet.next()) {
                    String passengerFirstName = userResultSet.getString("firstName");
                    String passengerLastName = userResultSet.getString("lastName");

                    // Get seatClass from SEAT table
                    String seatClassQuery = "SELECT seatClass FROM SEAT WHERE seatNumber = ? AND flightNumber = ?";
                    try (PreparedStatement seatClassStatement = DatabaseConnection.dbConnect.prepareStatement(seatClassQuery)) {
                        seatClassStatement.setString(1, seatNum);
                        seatClassStatement.setString(2, flightNum);
                        ResultSet seatClassResultSet = seatClassStatement.executeQuery();

                        if (seatClassResultSet.next()) {
                            String seatClass = seatClassResultSet.getString("seatClass");

                            // Insert the ticket into the TICKET table
                            String insertTicketQuery = "INSERT INTO TICKET (ticketNumber, flightNumber, passenger_fName, passenger_lName, seatNumber, seatClass, userID) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement insertTicketStatement = DatabaseConnection.dbConnect.prepareStatement(insertTicketQuery)) {
                                insertTicketStatement.setString(1, ticketNumber);
                                insertTicketStatement.setString(2, flightNum);
                                insertTicketStatement.setString(3, passengerFirstName);
                                insertTicketStatement.setString(4, passengerLastName);
                                insertTicketStatement.setString(5, seatNum);
                                insertTicketStatement.setString(6, seatClass);
                                insertTicketStatement.setInt(7, userID);

                                int rowsInserted = insertTicketStatement.executeUpdate();

                                if (rowsInserted > 0) {
                                    System.out.println("Ticket " + ticketNumber + " created successfully!");
                                } else {
                                    System.out.println("Failed to create ticket.");
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

    }


    public List<Ticket> getUserTickets(int userID) {
        List<Ticket> userTickets = new ArrayList<>();

        // SQL query to retrieve tickets based on userID
        String query = "SELECT * FROM TICKET WHERE userID = ?";
        try (PreparedStatement statement = DatabaseConnection.dbConnect.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and create Ticket objects
            while (resultSet.next()) {
                String ticketNumber = resultSet.getString("ticketNumber");
                String flightNumber = resultSet.getString("flightNumber");
                String seatNumber = resultSet.getString("seatNumber");

                // Retrieve flight information
                Flight flight = getFlightInformation(flightNumber);

                // Retrieve seat information
                Seat seat = getSeatInformation(seatNumber);

                // Retrieve user information (passenger)
                User passenger = getUserInformation(userID);

                // Create Ticket object and add it to the list
                Ticket ticket = new Ticket(ticketNumber, flight, passenger, seat);
                userTickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        return userTickets;
    }

    private Flight getFlightInformation(String flightNumber) throws SQLException {
        // Retrieve flight information from the database based on flightNumber
        String flightQuery = "SELECT * FROM FLIGHT WHERE flightNumber = ?";
        try (PreparedStatement flightStatement = DatabaseConnection.dbConnect.prepareStatement(flightQuery)) {
            flightStatement.setString(1, flightNumber);
            ResultSet flightResultSet = flightStatement.executeQuery();
    
            if (flightResultSet.next()) {
    
                // Retrieve data from the result set and create Flight objects
                String crewID = flightResultSet.getString("crewID");
                String destCountry = flightResultSet.getString("destination_country");
                String destCity = flightResultSet.getString("destination_city");
                String originCountry = flightResultSet.getString("origin_country");
                String originCity = flightResultSet.getString("origin_city");
                int capacity = flightResultSet.getInt("capacity");
                String departureDate = flightResultSet.getString("departureDate");
                String arrivalDate = flightResultSet.getString("arrivalDate");
                int aircraftID = flightResultSet.getInt("aircraftID");
                String aircraftModel = flightResultSet.getString("aircraftModel");
    
                // Create a Flight object and add it to the HashMap
                return new Flight(flightNumber, crewID,
                        new Location(destCity, destCountry),
                        new Location(originCity, originCountry),
                        capacity, departureDate, arrivalDate,
                        new Aircraft(aircraftID, aircraftModel, capacity));
            }
        }
    
        return null; // Handle appropriately if flight information is not found
    }
    

    private Seat getSeatInformation(String seatNumber) throws SQLException {
        // Retrieve seat information from the database based on seatNumber
        String seatQuery = "SELECT * FROM SEAT WHERE seatNumber = ?";
        try (PreparedStatement seatStatement = DatabaseConnection.dbConnect.prepareStatement(seatQuery)) {
            seatStatement.setString(1, seatNumber);
            ResultSet seatResultSet = seatStatement.executeQuery();

            if (seatResultSet.next()) {
                // Construct and return a Seat object
                return new Seat(
                        seatResultSet.getString("seatNumber"),
                        seatResultSet.getString("seatClass"),
                        seatResultSet.getBoolean("isBooked")
                );
            }
        }

        return null; 
    }


    public User getUserInformation(int userID) {
        
        String userQuery = "SELECT * FROM USERS WHERE userID = ?";
        try (PreparedStatement userStatement = DatabaseConnection.dbConnect.prepareStatement(userQuery)) {
            userStatement.setInt(1, userID);

            try (ResultSet resultSet = userStatement.executeQuery()) {
                if (resultSet.next()) {
                    // User found, create a User object with user details
                    return new User(
                        resultSet.getInt("userID"),
                        resultSet.getBoolean("isRegistered"),
                        new Name(resultSet.getString("firstName"), resultSet.getString("lastName")),
                        new Address(resultSet.getString("street"), resultSet.getString("city"), resultSet.getString("country")),
                        resultSet.getLong("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("pass"),
                        resultSet.getString("accessLevel")
                    );
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



    //returns a map of all of the seats associated with an aircraft 
    public Map<String, Seat> browseSeatMap(String flightNum) {
        Map<String, Seat> seatMap = new HashMap<>();

        String sql = "SELECT * FROM SEAT WHERE flightNumber = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            // Set the flightNum parameter before executing the query
            preparedStatement.setString(1, flightNum);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Assuming Seat class has a constructor that takes relevant parameters
                    Seat seat = new Seat(
                            resultSet.getString("seatNumber"),
                            resultSet.getString("seatClass"),
                            resultSet.getBoolean("isBooked"));
                            //resultSet.getDouble("price")
                    
                    seatMap.put(seat.getSeatNumber(), seat);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Seat> entry : seatMap.entrySet()) {
            String seatNumber = entry.getKey();
            Seat seat = entry.getValue();
            //String seatStatus = seat.isBooked() ? "Booked" : "Available";
            String seatClass = seat.getSeatClass();
            double price = 50;
    
            System.out.println("Seat Number: " + seatNumber + ", Class: " + seatClass + ", Status: "  + ", Price: " + seatNumber + ", Class: ");
        }

        return seatMap;
    }

    // function for user to delete their ticket and make seat available again 
    // seatNum not being considered because a user may want multiple tickets to cancel (i.e. family travel)
    public void cancelFlight(String flightNum, int userID) {
        try {
            // update seat before deleting ticket so that seatNum is properly found
            // Update SEAT records associated with the flight and userID
            // will find seat from ticket using userID and flightNumber to ensure exact booking is being made available
            String updateSeatQuery = "UPDATE SEAT SET isBooked = FALSE WHERE flightNumber = ? AND seatNumber IN (SELECT seatNumber FROM TICKET WHERE flightNumber = ? AND userID = ?)";
            try (PreparedStatement updateSeatStatement = DatabaseConnection.dbConnect.prepareStatement(updateSeatQuery)) {
                updateSeatStatement.setString(1, flightNum);
                updateSeatStatement.setString(2, flightNum);
                updateSeatStatement.setInt(3, userID);
                int seatRowsAffected = updateSeatStatement.executeUpdate();
                System.out.println(seatRowsAffected + " seats made available.");
            }

            // Delete TICKET records associated with the flight and userID
            String deleteTicketQuery = "DELETE FROM TICKET WHERE flightNumber = ? AND userID = ?";
            try (PreparedStatement deleteTicketStatement = DatabaseConnection.dbConnect.prepareStatement(deleteTicketQuery)) {
                deleteTicketStatement.setString(1, flightNum);
                deleteTicketStatement.setInt(2, userID);
                int ticketRowsAffected = deleteTicketStatement.executeUpdate();
                System.out.println(ticketRowsAffected + " tickets canceled.");
            }

            // NEED GUI TO PROVIDE CANCELLATION NOTIFICATION TO USER AND NOTIF TO ADMIN ABOUT SEATS BEING AVAILABLE
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    


public boolean setUserRegistered(String email, String password) {
    String sql = "UPDATE USERS SET isRegistered = true WHERE email = ? AND pass = ?";

    try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        int rowsAffected = preparedStatement.executeUpdate();

        // If one row is affected, it means the update was successful
        return rowsAffected == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }}



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
    public boolean getRegisterAccessLevel(String email, String password) {

        String sql = "SELECT isRegistered FROM USERS WHERE email = ? AND pass = ?";
    
        try (PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("getting register status");
                    return resultSet.getBoolean("isRegistered");
                }
                else {
                    // No user found with the given email and password
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    private String generateUniqueTicketNumber() {
        // Define the format for the ticket number
        String ticketPrefix = "T";

        // Generate a random 3-digit number
        String randomTicketNumber = generateRandomTicketNumber(3);

        // Check if the generated ticket number is unique
        while (!ticketNumUnique(ticketPrefix + randomTicketNumber)) {
            randomTicketNumber = generateRandomTicketNumber(3);
        }

        // Combine the prefix and the unique random number to create the final ticket number
        return ticketPrefix + randomTicketNumber;
    }

    private String generateRandomTicketNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        return sb.toString();
    }

    // Method to check if a ticket number exists in the TICKET table
    public static boolean ticketNumUnique(String ticketNumber) {
        String query = "SELECT COUNT(*) AS count FROM TICKET WHERE ticketNumber = ?";
        
        try (
            PreparedStatement preparedStatement = DatabaseConnection.dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, ticketNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // If count > 0, the ticket number already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false in case of exceptions or if the query fails
    }
    
}



    // // user has seen seatMap and chosen a seat, this function takes that seatNumber and changes isBooked to true in the SEAT table, 
    // // and creates a ticket for them associated with their ID, name, etc.
    // public void selectSeat(String seatNum, String flightNum, int userID) {
    //     // change seat row isBooked to true
    //     // SQL query to update the isBooked attribute to TRUE based on seatNumber and flightNumber
    //     String updateQuery = "UPDATE SEAT SET isBooked = TRUE WHERE seatNumber = ? AND flightNumber = ?";

    //     try (PreparedStatement updateStatement = DatabaseConnection.dbConnect.prepareStatement(updateQuery)) {
    //         // Set values for the placeholders in the update query
    //         updateStatement.setString(1, seatNum);
    //         updateStatement.setString(2, flightNum);

    //         // Execute the update query
    //         int rowsAffected = updateStatement.executeUpdate();

    //         if (rowsAffected > 0) {
    //             System.out.println("Seat " + seatNum + " on Flight " + flightNum + " booked successfully!");
    //         } else {
    //             System.out.println("No matching seat found for the specified seatNumber and flightNumber.");
    //         }

    //         // create Ticket Number
    //         // already have flightNum
    //         // get first and last name from userID
            
    //         // already have seatNum
    //         // get seatClass
    //         // already have userID

    //         // Create Ticket Number
    //         String ticketNumber = generateUniqueTicketNumber();

    //         // Get user information from USERS table
    //         String userQuery = "SELECT firstName, lastName FROM USERS WHERE userID = ?";
    //         try (PreparedStatement userStatement = DatabaseConnection.dbConnect.prepareStatement(userQuery)) {
    //             userStatement.setInt(1, userID);
    //             ResultSet userResultSet = userStatement.executeQuery();

    //             if (userResultSet.next()) {
    //                 String passengerFirstName = userResultSet.getString("firstName");
    //                 String passengerLastName = userResultSet.getString("lastName");

    //                 // Get seatClass from SEAT table
    //                 String seatClassQuery = "SELECT seatClass FROM SEAT WHERE seatNumber = ? AND flightNumber = ?";
    //                 try (PreparedStatement seatClassStatement = DatabaseConnection.dbConnect.prepareStatement(seatClassQuery)) {
    //                     seatClassStatement.setString(1, seatNum);
    //                     seatClassStatement.setString(2, flightNum);
    //                     ResultSet seatClassResultSet = seatClassStatement.executeQuery();

    //                     if (seatClassResultSet.next()) {
    //                         String seatClass = seatClassResultSet.getString("seatClass");

    //                         // Insert the ticket into the TICKET table
    //                         String insertTicketQuery = "INSERT INTO TICKET (ticketNumber, flightNumber, passenger_fName, passenger_lName, seatNumber, seatClass, userID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    //                         try (PreparedStatement insertTicketStatement = DatabaseConnection.dbConnect.prepareStatement(insertTicketQuery)) {
    //                             insertTicketStatement.setString(1, ticketNumber);
    //                             insertTicketStatement.setString(2, flightNum);
    //                             insertTicketStatement.setString(3, passengerFirstName);
    //                             insertTicketStatement.setString(4, passengerLastName);
    //                             insertTicketStatement.setString(5, seatNum);
    //                             insertTicketStatement.setString(6, seatClass);
    //                             insertTicketStatement.setInt(7, userID);

    //                             int rowsInserted = insertTicketStatement.executeUpdate();

    //                             if (rowsInserted > 0) {
    //                                 System.out.println("Ticket " + ticketNumber + " created successfully!");
    //                             } else {
    //                                 System.out.println("Failed to create ticket.");
    //                             }
    //                         }
    //                     }
    //                 }
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //             // Handle exceptions as needed
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         // Handle exceptions as needed
    //     }

    // }
