package Entity;

public class Aircraft {
    private int aircraftID;
    private String model;
    private int capacity;

    // Constructor
    public Aircraft(int aircraftID, String model, int capacity) {
        this.aircraftID = aircraftID;
        this.model = model;
        this.capacity = capacity;
    }


    public String getModel() {
        return model;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public String getAircraftModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAircraftID(int aircraftID) {
        this.aircraftID = aircraftID;
    }

    public void setAircraftModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
