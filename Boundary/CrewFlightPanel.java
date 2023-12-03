package Boundary;
import javax.swing.*;

import Controller.UserController;
import Entity.Location;
import Entity.Flight;
import Entity.Aircraft;
import Entity.Seat;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import Boundary.BrowseAdminFlightsPanel;

public class CrewFlightPanel extends JPanel {
    public static String flightSelected;
    Map<String, Seat> seatMap;
    private JButton bookButton;
    private JButton refreshButton;
    private UserController usc;
    
    

    public CrewFlightPanel(UserController usc) {
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

    bookButton = new JButton("Check Information on Selected Flight");
    bookButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow != -1) {
                flightSelected = (String) flightTable.getValueAt(selectedRow, 0);

                // Your existing logic for booking the flight here

                // Show Crew Navigation Card after booking logic
                AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Crew Navigation Card");
            } else {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(CrewFlightPanel.this),
                        "Check Information on Selected Flight.");
            }
        }
    });

    // Add the bookButton to the panel
    add(bookButton, BorderLayout.SOUTH);


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

        public String getSelectedFlightNumber() {
        return flightSelected;
    }
}




