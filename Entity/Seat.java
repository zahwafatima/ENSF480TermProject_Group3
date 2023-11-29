package Entity;

public class Seat {
    private String seatNumber;
    private String seatClass;
    private boolean isBooked;
    private double price;

    // Constructor
    public Seat(String seatNumber, String seatClass, double price) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isBooked = false; // Initialize as not booked
    }

    // Getters and Setters
    public String getSeatNumber() {
        return seatNumber;
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


}
