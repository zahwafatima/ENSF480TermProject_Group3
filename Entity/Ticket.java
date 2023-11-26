package Entity;
public class Ticket {
    private String ticketNumber;
    private Flight flightNumber;
    private User passenger;
    private Seat seat;
    private Seat ticketClass; // Renamed from 'class'

    // Constructor
    public Ticket(String ticketNumber, Flight flightNumber, User passenger, Seat seat, Seat ticketClass) {
        this.ticketNumber = ticketNumber;
        this.flightNumber = flightNumber;
        this.passenger = passenger;
        this.seat = seat;
        this.ticketClass = ticketClass;
    }

    // Getters
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

    // Methods
    public String generateTicketNumber() {
        String ticketNumber = "";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * alphabet.length());
            ticketNumber += alphabet.charAt(random);
        }
        return ticketNumber;
    }

}
