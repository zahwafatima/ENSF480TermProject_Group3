package Boundary;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import Controller.UserController;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Entity.Seat;
import Entity.Flight;
import Entity.User;

public class BrowsePassengersPanel extends JPanel {
    public static String flightSelected;
    private Map<String, Seat> seatMap;
    private JButton refreshButton;
    private UserController usc;
    // private CrewFlightPanel crewFlightPanel; // Commented out to break the circular dependency

    public BrowsePassengersPanel(UserController usc) {
        setLayout(new BorderLayout());
        this.usc = usc;
        Map<String, List<String>> passMap = usc.browsePassengers(flightSelected);

        String[] columnNames = {"Ticket Number", "First Name", "Last Name", "Seat Number", "Seat Class", "User ID"};
        Object[][] data = new Object[passMap.size()][columnNames.length];

        int row = 0;
        for (Map.Entry<String, List<String>> entry : passMap.entrySet()) {
            List<String> passengerDetails = entry.getValue();

            for (int col = 0; col < passengerDetails.size(); col++) {
                data[row][col] = passengerDetails.get(col);
            }

            row++;
        }

        JTable passengerTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(passengerTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh Passengers");
        refreshButton.addActionListener(e -> {
            // Implement logic to refresh the list of passengers
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(refreshButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
