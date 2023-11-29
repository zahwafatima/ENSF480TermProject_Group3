import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class AirlineReservationSystem extends JFrame {

    private CardLayout cardLayout;
    private  static JPanel cardsPanel; // This will contain all the "pages"
    private final String LOGIN_CARD = "Login Card";
    private final String SIGNUP_CARD = "Sign-up Card";
    private final String BROWSE_FLIGHTS_CARD = "Browse Flights Card";
    private final String SEAT_MAP_CARD = "Seat Map Card";
    private final String CHECKOUT_CARD = "Checkout Card";

    public AirlineReservationSystem() {
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Create the card panels.
        JPanel loginPanel = new JPanel();
        JPanel signupPanel = new JPanel();
        JPanel browseFlightsPanel = new JPanel();
        JPanel seatMapPanel = new JPanel();;
        JPanel checkoutPanel = new JPanel();
        cardsPanel.add(loginPanel, LOGIN_CARD);
        cardsPanel.add(signupPanel, SIGNUP_CARD);
        cardsPanel.add(browseFlightsPanel, BROWSE_FLIGHTS_CARD);
        cardsPanel.add(seatMapPanel, SEAT_MAP_CARD);
        cardsPanel.add(checkoutPanel, CHECKOUT_CARD);

         loginPanel = createLoginPanel(cardsPanel);
         signupPanel = createSignUpPanel();
        browseFlightsPanel = createBrowseFlightsPanel();
        seatMapPanel = createSeatMapPanel();
         checkoutPanel = createCheckoutPanel();

        // Add the cards to the cardsPanel.
        

        // Show the initial card.
        cardLayout.show(cardsPanel, LOGIN_CARD);

        // Add the cardsPanel to the frame.
        add(cardsPanel);

        // Set up the frame.
        setTitle("Airline Reservation System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createLoginPanel(JPanel cardsPanel) {
        LoginPanel loginPanel = new LoginPanel();
        return loginPanel;
    }
    private JPanel createSignUpPanel() {
        SignUpPanel signupPanel = new SignUpPanel();
        return signupPanel;
    }
    private JPanel createBrowseFlightsPanel() {
        BrowseFlightsPanel browseFlightsPanel = new BrowseFlightsPanel();
        JButton nextButton = new JButton("Select Flight and Browse Seats");
        nextButton.addActionListener(e -> cardLayout.show(cardsPanel, SEAT_MAP_CARD));
        browseFlightsPage.add(nextButton, BorderLayout.SOUTH);
        return browseFlightsPanel;
        // // Create components for browseFlightsPanel...
        // JPanel panel = new JPanel(new BorderLayout());
        //  JButton nextButton = new JButton("Select Flight and Browse Seats");
        // nextButton.addActionListener(e -> cardLayout.show(cardsPanel, SEAT_MAP_CARD));
        //  panel.add(nextButton, BorderLayout.SOUTH);
        // return panel;
    }

    private JPanel createSeatMapPanel() {
        SeatMapPa seatMapPanel = new SeatMapPa();
        JButton nextButton = new JButton("Choose Seats and Checkout");
         nextButton.addActionListener(e -> cardLayout.show(cardsPanel, CHECKOUT_CARD));
         seatMapPanel.add(nextButton, BorderLayout.SOUTH);
        // Add any additional components or listeners if needed
        return seatMapPanel;
        // // Create components for seatMapPanel...
        // JPanel panel = new JPanel(new BorderLayout());
        // JButton nextButton = new JButton("Choose Seats and Checkout");
        // nextButton.addActionListener(e -> cardLayout.show(cardsPanel, CHECKOUT_CARD));
        // panel.add(nextButton, BorderLayout.SOUTH);
        // return panel;
    }

    private JPanel createCheckoutPanel() {

     Set<String> selectedSeats = new HashSet<>();
    selectedSeats.add("10A"); // Dummy data for selected seats

    CheckoutPanel checkoutPanel = new CheckoutPanel(selectedSeats);
    return checkoutPanel;
        // Create components for checkoutPanel...
        // JPanel panel = new JPanel(new BorderLayout());
        // JButton nextButton = new JButton("Confirm Booking");
        // nextButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Booking Confirmed!"));
        // panel.add(nextButton, BorderLayout.SOUTH);
        // return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineReservationSystem frame = new AirlineReservationSystem();
            frame.setVisible(true);
        });
    }
}
