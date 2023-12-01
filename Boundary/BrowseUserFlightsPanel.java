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


public class BrowseUserFlightsPanel extends JPanel {
    private JList<String> flightsList;
    private JButton bookButton;
    private JButton refreshButton;
    private UserController usc;
    static Flight selectedFlight;

    public BrowseUserFlightsPanel(UserController usc) {
        setLayout(new BorderLayout());
        this.usc = usc;

    }
}