package Entity;
import java.util.ArrayList;

public class Flight {
    private String flightNumber;
    private String crewID;
    //private Airline airline;
    private Location destination;
    private Location origin;
    private int seatCapacity;
    private String departureTime;
    private String arrivalTime;
    private Aircraft aircraft;
    
    // Constructor
    public Flight(String flightNumber, String crewID, Location destination, Location origin, int seatCapacity, String departureTime, String arrivalTime, Aircraft aircraft) {
        this.flightNumber = flightNumber;
        this.crewID = crewID;
        //this.airline = airline;
        this.destination = destination;
        this.origin = origin;
        this.seatCapacity= seatCapacity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.aircraft = aircraft; // need copy ctor???
    }

    // Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    // public Airline getAirline() {
    //     return airline;
    // }

    public String getCrewID() {
        return crewID;
    }

    public Location getDestination() {
        return destination;
    }

    public Location getOrigin() {
        return origin;
    }

    public int getCapacity() {
        return seatCapacity;
    }

    public String getDepartureDate() {
        return departureTime;
    }

    public String getArrivalDate() {
        return arrivalTime;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    // Setters
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String setCrewID(String crewID) {
        this.crewID = crewID;
    }

    // public void setAirline(Airline airline) {
    //     this.airline = airline;
    // }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public void setseatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public void setDepartureDate(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalDate(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

}
