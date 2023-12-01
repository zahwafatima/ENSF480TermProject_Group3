package Boundary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import Entity.Seat;

public class SeatMapPanel extends JPanel {
    // private Map<Seat, JButton> seatButtons;
    // private final int rows = 10; // number of rows
    // private final char[] sides = {'A', 'B', 'C', 'D', 'E', 'F'}; // Seat letters
    // // flight object 
    // public SeatMapPanel() {
    //     setLayout(new GridLayout(0, 4, 10, 10)); // 4 columns to simulate a typical narrow-body aircraft with a single aisle, with space for the aisle

    //     seatButtons = new HashMap<>();
    //     createSeats();
    // }

    // private void createSeats() {
    //     for (int i = 1; i <= rows; i++) {
    //         for (char side : sides) {
    //             if (side == 'D') { // Just before 'D', let's add a spacer to represent the aisle
    //                 add(Box.createHorizontalStrut(15));
    //             }

    //             String seatId = "" + i + side;
    //             JButton seatButton = new JButton(seatId);
    //             seatButton.addActionListener((ActionEvent e) -> {
    //                 JButton source = (JButton) e.getSource();
    //                 String seat = source.getText();
    //                 int confirm = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(this),
    //                         "Do you want to book seat " + seat + "?",
    //                         "Confirm Seat", JOptionPane.YES_NO_OPTION);
    //                 if (confirm == JOptionPane.YES_OPTION) {
    //                     source.setEnabled(false); // Simulate booking the seat by disabling the button
    //                     source.setBackground(Color.RED); // Change color to indicate booking
    //                     source.setText(seat + " - Booked");
    //                 }
    //             });

    //             add(seatButton);
    //             seatButtons.put(seatId, seatButton);
    //         }
    //     }
    // }

    // // If you want to expose the seat buttons map for external manipulation
    // public Map<String, JButton> getSeatButtons() {
    //     return seatButtons;
    // }
}
