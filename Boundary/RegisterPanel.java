package Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.GuestUser;

public class RegisterPanel extends JPanel {
    private GuestUser guestUser;

    public RegisterPanel(GuestUser guestUser) {
        this.guestUser = guestUser;

        JButton registerButton = new JButton("Register for Membership");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call subscribeToMembership
                guestUser.subscribeToMembership();

            }
        });

        // Add components to the panel
        add(registerButton);
        // Add other components as needed
    }
}
