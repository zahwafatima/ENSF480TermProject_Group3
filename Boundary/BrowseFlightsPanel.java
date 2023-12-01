package Boundary;
import javax.swing.*;

import Controller.UserController;
import Entity.Location;
import Entity.Flight;
import Entity.Aircraft;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class BrowseFlightsPanel extends JPanel {
    private JList<String> flightsList;
    private JButton bookButton;
    private JButton refreshButton;
    private UserController usc;
    static Flight selectedFlight;

    public BrowseFlightsPanel(UserController usc) {
        setLayout(new BorderLayout());
        this.usc = usc;

        Map<String, Flight> flightMap = usc.browseFlightsByDate(usc.getDate());
        // Dummy data for flights
        String[] columnNames = {"Flight Number", "Destination", "Departure Date"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable flightTable = new JTable(model);

        for (Flight flight : flightMap.values()) {
             {
                Object[] row = new Object[]{
                    flight.getFlightNumber(),
                    flight.getDestination().getCity(),
                    flight.getOrigin().getCity(),
                };
                model.addRow(row);
            }
        }
        JScrollPane scrollPane = new JScrollPane(flightTable);
        add(scrollPane, BorderLayout.CENTER);

        bookButton = new JButton("Book Selected Flight");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFlight = flightsList.getSelectedValue();
                if (selectedFlight != null) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(BrowseFlightsPanel.this),
                            "You have booked " + selectedFlight);
                            AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Seat Map Card");
                            
                } else {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(BrowseFlightsPanel.this),
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
