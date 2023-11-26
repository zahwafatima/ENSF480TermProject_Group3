package Entity;
public class Name {
    private String firstName;
    private String lastName;

    // Constructor
    public Name(String first, String last) {
        this.firstName = first;
        this.lastName = last;
    }

    //setters and getters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first) {
        this.firstName = first;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

}
