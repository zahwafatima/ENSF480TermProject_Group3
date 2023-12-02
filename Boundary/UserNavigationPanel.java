package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserNavigationPanel extends JPanel {

    public UserNavigationPanel() {
        JButton currentFlightButton = new JButton("View all current flights");
        currentFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "User Browse Flights Card");
            }
        });

        JButton registerButton = new JButton("Register for membership");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Register membership Card");
            }
        });

        // Add buttons to the panel
        add(currentFlightButton);
        add(registerButton);
    }
}

