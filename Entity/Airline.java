package Entity;

public class Airline {
    private String name;
    private AirlineController airlineController;

    // Constructor
    public Airline(String name, AirlineController airlineController) {
        this.name = name;
        this.airlineController = airlineController;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for airlineController
    public AirlineController getAirlineController() {
        return airlineController;
    }

    // Setter for airlineController
    public void setAirlineController(AirlineController airlineController) {
        this.airlineController = airlineController;
    }
}
