import Controller.DatabaseConnection;

public class FlightReservation{
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        if (dbConnection.dbConnect != null) {
            System.out.println("Connection to the database is successful!");
        } else {
            System.out.println("Failed to connect to the database. Check your connection details.");
        }
    }
}

