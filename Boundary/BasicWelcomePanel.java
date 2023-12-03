package Boundary;

import javax.swing.*;
import java.awt.*;

public class BasicWelcomePanel implements WelcomePanel {
    @Override
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Welcome to the Flight Reservation System"));
        return panel;
    }
}

