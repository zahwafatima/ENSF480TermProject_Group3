package Boundary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import Entity.Seat;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import Entity.Seat;
import Controller.UserController;
import Boundary.BrowseUserFlightsPanel; // Import your controller

public class SeatMapPanel extends JPanel {
    private UserController usc;
    private Map<String, JButton> seatButtons;
    private Map<String, Seat> seatMap; // Add this to store seat details
    private final int rows = 10; // number of rows
    private final char[] sides = {'A', 'B', 'C', 'D', 'E', 'F'}; // Seat letters
    private JButton selectSeatButton;
    static String seatString;
    private Seat seat;

    public SeatMapPanel(UserController usc) {
        this.usc = usc;
        setLayout(new GridLayout(0, 4, 10, 10)); // GridLayout setup

        seatButtons = new HashMap<>();
        
         // Fetch seat data
        selectSeatButton = new JButton("Select Seats");
        selectSeatButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                seatMap = usc.browseSeatMap(BrowseUserFlightsPanel.flightSelected);
                JDialog dg =  selectSeatDialog();
                dg.setVisible(true);
            }
        });
        add(selectSeatButton);
        
    }

    public void printSeatMap() {
        for (Map.Entry<String, Seat> entry : this.seatMap.entrySet()) {
            String seatNumber = entry.getKey();
            Seat seat = entry.getValue();
            JButton seatButton = new JButton(seat.getSeatNumber());
            //String seatStatus = seat.isBooked() ? "Booked" : "Available";
            //String seatClass = seat.getSeatClass();
            
            
        }
    }
    
    public Map<String, JButton> getSeatButtons() {
        return seatButtons;
    }

    public JDialog selectSeatDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Select Seat");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(300, 400); // Set the size of the dialog
        dialog.setLocationRelativeTo(this);
        JButton seatButton;
        for (Map.Entry<String, Seat> entry : this.seatMap.entrySet()) {
            String seatNumber = entry.getKey();
            Seat seat = entry.getValue();
           

            if (!seat.isBooked()) {
                System.out.println("hi");
                seatButton = new JButton("booked");
                seatButton.setEnabled(false); // Disable the button if the seat is already booked
                seatButton.setBackground(Color.RED);
                seatButton.setOpaque(isOpaque()); // Change color to indicate booking
            }else{seatButton = new JButton(seatNumber);
            seatButton.setBackground(Color.GREEN);
            seatButton.setOpaque(isOpaque());
        }

            seatButton.addActionListener((ActionEvent e) -> {
                JButton source = (JButton) e.getSource();
                seatString = source.getText();

                // Add your if statement here
                if (!seat.isBooked()) { // Check if the seat is not already booked
                    source.setEnabled(false); // Simulate booking the seat by disabling the button
                    source.setBackground(Color.RED); // Change color to indicate booking
                    source.setText(seat + " - Booked");
                } else {
                    // Handle the case where the seat is already booked
                    // You can display a message or take other actions
                    System.out.println("Seat " + seatNumber + " is already booked.");
                }
            });

            dialog.add(seatButton);
            seatButtons.put(seat.getSeatNumber(), seatButton);
        }

        JButton nextButton = new JButton("Choose Seats and Checkout");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Checkout Card");
                dialog.dispose();
            }
        });

        dialog.add(nextButton, BorderLayout.SOUTH);

        return dialog;
    }

    

}
