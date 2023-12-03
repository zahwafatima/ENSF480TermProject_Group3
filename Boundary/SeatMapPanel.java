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
    private String seat;

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

    private void createSeats(JDialog dialog) {
        for (Map.Entry<String, Seat> entry : this.seatMap.entrySet()) {
            String seatNumber = entry.getKey();
            Seat seat = entry.getValue();
            JButton seatButton = new JButton(seat.getSeatNumber());
            seatButton.addActionListener((ActionEvent e) -> {
                JButton source = (JButton) e.getSource();
               this.seat = source.getText();
                int confirm = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(this),
                        "Do you want to book seat " + seat + "?",
                        "Confirm Seat", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    source.setEnabled(false); // Simulate booking the seat by disabling the button
                    source.setBackground(Color.RED); // Change color to indicate booking
                    source.setText(seat + " - Booked");
                }
            });
            //String seatStatus = seat.isBooked() ? "Booked" : "Available";
            add(seatButton);
            seatButtons.put(seat.getSeatNumber(), seatButton);
            

            
        }
            
                

                // if(seatMap.containsKey(seatId)){
                //     // if (seatMap.get(seatId).isBooked()) {
                //     // seatButton.setEnabled(false); // Disable if booked
                //     seatButton.setBackground(Color.RED); // Change color
                //  }
                // // else {seatButton.setBackground(Color.GREEN);}
                // // }

                // seatButton.addActionListener((ActionEvent e) -> {
                //     // Your existing action listener code
                // });

                // add(seatButton);
                // seatButtons.put(seatId, seatButton);
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
    public JDialog selectSeatDialog(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Select Seat");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(300, 400); // Set the size of the dialog
        dialog.setLocationRelativeTo(this);
        for (Map.Entry<String, Seat> entry : this.seatMap.entrySet()) {
            String seatNumber = entry.getKey();
            Seat seat = entry.getValue();
            JButton seatButton = new JButton(seat.getSeatNumber());
            //String seatStatus = seat.isBooked() ? "Booked" : "Available";
            seatButton.addActionListener((ActionEvent e) -> {
                JButton source = (JButton) e.getSource();
               this.seat = source.getText();
                    source.setEnabled(false); // Simulate booking the seat by disabling the button
                    source.setBackground(Color.RED); // Change color to indicate booking
                    source.setText(seat + " - Booked");
                
            });
            dialog.add(seatButton);
            seatButtons.put(seat.getSeatNumber(), seatButton);
        }
        JButton nextButton = new JButton("Choose Seats and Checkout");
         nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Checkout Card");}});
         dialog.add(nextButton, BorderLayout.SOUTH);
                // seatButton.addActionListener((ActionEvent e) -> {
                //     // Your existing action listener code
                // });

                // dialog.add(seatButton);
                // seatButtons.put(seatId, seatButton);
        //     }
        // }
        return dialog;
    }
    

}
