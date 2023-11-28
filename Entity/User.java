package Entity;
import java.util.Date;

import controller.Login;


public class User {
    private boolean isRegistered; 
    private Name name;
    private Address address;
    private long phoneNumber;
    private String email;
    private String pass;
    private String accessLevel;
    private Login login;
    

    // Constructor
    public User(boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        this.isRegistered = isRegistered;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pass = pass;
        this.accessLevel = accessLevel;
        login.addUser(email, pass);

    }

    // Getters and setters
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
