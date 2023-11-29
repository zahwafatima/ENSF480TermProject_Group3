import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowseFlightsPanel extends JPanel {
    private JList<String> flightsList;
    private JButton bookButton;
    private JButton refreshButton;

    public BrowseFlightsPanel() {
        setLayout(new BorderLayout());

        // Dummy data for flights
        String[] flightsData = {
            "Flight 101 - New York to London",
            "Flight 202 - Berlin to Madrid",
            "Flight 303 - Paris to Rome",
            "Flight 404 - Tokyo to San Francisco"
        };

        flightsList = new JList<>(flightsData);
        add(new JScrollPane(flightsList), BorderLayout.CENTER);

        bookButton = new JButton("Book Selected Flight");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFlight = flightsList.getSelectedValue();
                if (selectedFlight != null) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(BrowseFlightsPage.this),
                            "You have booked " + selectedFlight);
                } else {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(BrowseFlightsPage.this),
                            "Please select a flight to book.");
                }
            }
        });

        refreshButton = new JButton("Refresh Flights");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to refresh the list of flights
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(bookButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
