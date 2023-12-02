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

import Boundary.BrowseAdminFlightsPanel;

public class BrowseUserFlightsPanel extends JPanel {
    private JButton bookButton;
    private JButton refreshButton;
    private UserController usc;
    

    public BrowseUserFlightsPanel(UserController usc) {
        setLayout(new BorderLayout());
        this.usc = usc;
        Map<String, Flight> flightMap = usc.browseAllFlights();

        String[] columnNames = {
            "Flight Number",
            "Destination Country", 
            "Destination City",
            "Origin Country",
            "Origin City",
            "Departure Date",
            "Arrival Date"
        };        

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable flightTable = new JTable(model);

        for (Flight flight : flightMap.values()) {
            Object[] row = new Object[]{
                flight.getFlightNumber(),
                flight.getDestination().getCountry(),
                flight.getDestination().getCity(),
                flight.getOrigin().getCountry(),
                flight.getOrigin().getCity(),
                flight.getDepartureDate(),
                flight.getArrivalDate()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(flightTable);
        add(scrollPane, BorderLayout.CENTER);

        bookButton = new JButton("Book Selected Flight");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = flightTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedFlight = (String) flightTable.getValueAt(selectedRow, 0);
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(BrowseUserFlightsPanel.this),
                            "You have booked " + selectedFlight);
                            // Implement your logic for booking the flight here
                } else {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(BrowseUserFlightsPanel.this),
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
