package Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ticket {
    private String ticketNumber;
    private Flight flight;
    private User passenger;
    private int price;
    private Seat seat;

    // Static map to store flightNumber-price associations
    private static final Map<Flight, Integer> flightPrices = new HashMap<>();

    // Constructor
    public Ticket(String ticketNumber, Flight flight, User passenger, Seat seat, Seat ticketClass) {
        this.ticketNumber = generateRandomTicketNumber();
        this.flightNumber = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.ticketClass = ticketClass;

        // Check if the flightNumber already has a price assigned
        if (!flightPrices.containsKey(flight)) {
            // If not, generate a new random price and associate it with the flightNumber
            Random random = new Random();
            int newPrice = random.nextInt(501) + 200;
            flightPrices.put(flight, newPrice);
        }
        this.price = flightPrices.get(flightNumber);
    }

    private String generateRandomTicketNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // Random number between 1000 and 9999
        return "T" + randomNumber;
    }

    // Getters
    public double getPrice() {
        return price;
    }


    public String getTicketNumber() {
        return ticketNumber;
    }

    public Flight getFlightNumber() {
        return flightNumber;
    }

    public User getPassenger() {
        return passenger;
    }

    public Seat getSeat() {
        return seat;
    }

    public Seat getTicketClass() {
        return ticketClass;
    }

    // Setters
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setFlightNumber(Flight flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setTicketClass(Seat ticketClass) {
        this.ticketClass = ticketClass;
    }


}
