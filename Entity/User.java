package Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import Controller.DatabaseConnection;
import Controller.Login;
import Controller.UserController;


public class User {

    DatabaseConnection dbConnection = DatabaseConnection.getOnlyInstance();

    private int userID;
    private boolean isRegistered; 
    private Name name;
    private Address address;
    private long phoneNumber;
    private String email;
    private String pass;
    private String accessLevel;
    private UserController userController; 
    private Login login = Login.getOnlyInstance();
    
    public User(){
        this.userID =generateUserID();
        this.isRegistered = false;
        this.name = new Name("N/A", "N/A");
        this.address = new Address("N/A", "N/A", "N/A");
        this.phoneNumber = 0000000000;
        this.email = "N/A";
        this.pass = "N/A";
        this.accessLevel = "N/A";
        
        login.addUser(this);
        //userController.addUserToDB(this);

        login.addUser(this); // Synchronize with Login class

    }

    // Constructor
    public User(boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        this.userID = generateUserID();
        this.isRegistered = isRegistered;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pass = pass;
        this.accessLevel = accessLevel;

        Login login = Login.getOnlyInstance();
        login.addUser(this); // Synchronize with Login class
        //userController.addUserToDB(this);
    }

    private int generateUserID(){
        // Generate a new userID
        try {do {
            Random random = new Random();
            userID =  1000 + random.nextInt(9000); // Generates a random 4-digit number
            } while (userIDExists(DatabaseConnection.dbConnect, userID));
        }catch (SQLException e) {
            
        }
    
        return userID;
    }

    private boolean userIDExists(Connection connection, int userID) throws SQLException {
    // Check if userID already exists in the database
        String query = "SELECT COUNT(*) FROM FLIGHTDB.USERS WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
    }
    return false;
    }

    public int calculatePayment(Ticket ticket){
        return ticket.getPrice();
    }


    // Getters and setters

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
   
    public boolean getIsRegistered(){
        return this.isRegistered;
    }

    public void setIsRegistered(boolean isRegistered){
        this.isRegistered = isRegistered;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String access) {
        this.accessLevel = access;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
