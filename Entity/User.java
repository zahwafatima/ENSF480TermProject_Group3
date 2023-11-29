package Entity;
import java.util.Date;

import Controller.Login;


public class User {
    private boolean isRegistered; 
    private Name name;
    private Address address;
    private long phoneNumber;
    private String email;
    private String pass;
    private String accessLevel;
    
    public User(){
        this.isRegistered = false;
        this.name = new Name("N/A", "N/A");
        this.address = new Address("N/A", "N/A", "N/A");
        this.phoneNumber = 0000000000;
        this.email = "N/A";
        this.pass = "N/A";
        this.accessLevel = "N/A";
        Login login = Login.getOnlyInstance();
        login.addUser(email, pass);
    }

    // Constructor
    public User(boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        this.isRegistered = isRegistered;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pass = pass;
        this.accessLevel = accessLevel;
        Login login = Login.getOnlyInstance();
        login.addUser(email, pass);

    }

    // Getters and setters
   
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
