package Entity;

public class Crew {
    private String name;
    private String role;
    private String employeeID;

    // Constructor
    public Crew(String name, String role, String employeeID) {
        this.name = name;
        this.role = role;
        this.employeeID = employeeID;
    }

    // Getter and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

}
