package Entity;

public class Seat {
    private String seatNumber;
    private String flightNumber;
    private String seatClass;
    private boolean isBooked;
    //private double price; will calc separate for each
    //private String ticketNumber; since a seat can have nay tikcet number it does not have to be predetermined
    // ticket number is generated and assigned to the seat using the flightNumber since only one type of seat exists on each plane

    // Constructor
    public Seat(String seatNumber, String flightNumber, String seatClass) {
        this.seatNumber = seatNumber;
        this.flightNumber = flightNumber;
        this.seatClass = seatClass;
        this.isBooked = false; // Initialize as not booked
   
    
    }

    // Getters and Setters
    public String getSeatNumber() {
        return seatNumber;
    }

    public String getflightNumber() {
        return flightNumber;
    }

    public String getticketNumber() {
        return ticketNumber;
    }
    
    public void setticketNumber(String ticketNumber){
        this.ticketNumber = ticketNumber;
    }

    public void setflightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookSeat() {
        isBooked = true;
    }

    public void unbookSeat() {
        isBooked = false;
    }

    // public double getPrice() {
    //     return price;
    // }

    // public void setPrice(double price) {
    //     this.price = price;
    // }
}
