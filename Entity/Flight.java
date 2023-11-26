package Entity;
import java.util.ArrayList;

public class Flight {
    private String flightNumber;
    private Airline airline;
    private Aircraft aircraft;
    private Location destination;
    private Location origin;
    private int seatCapacity;
    private String departureTime;
    private String arrivalTime;
    
    // Constructor
    public Flight(String flightNumber, Airline airline, Location destination, Location origin, int seatCapacity, String departureTime, String arrivalTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.destination = destination;
        this.origin = origin;
        this.seatCapacity= seatCapacity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public Airline getAirline() {
        return airline;
    }

    public Location getDestination() {
        return destination;
    }

    public Location getOrigin() {
        return origin;
    }

    public int getseatCapacity() {
        return seatCapacity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    // Setters
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public void setseatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

}
