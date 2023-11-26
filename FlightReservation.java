import java.sql.Connection;

public class FlightReservation{
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
