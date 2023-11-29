package Boundary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class CheckoutPanel extends JPanel {
    private Set<String> selectedSeats;
    private JLabel totalCostLabel;
    private JCheckBox insuranceCheckbox;
    private final int ticketPrice = 100;
    private final int insurancePrice = 20;

    public CheckoutPanel(Set<String> selectedSeats) {
        this.selectedSeats = selectedSeats;
        setLayout(new BorderLayout());

        // Insurance option
        insuranceCheckbox = new JCheckBox("Add Ticket Insurance ($20 per seat)");
        insuranceCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotalCost();
            }
        });

        // Total cost label
        totalCostLabel = new JLabel();
        updateTotalCost(); // Update initial cost

        // Confirm button
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle booking confirmation
                JOptionPane.showMessageDialog(CheckoutPanel.this, "Booking Confirmed!");
            }
        });

        add(insuranceCheckbox, BorderLayout.NORTH);
        add(totalCostLabel, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
    }

    private void updateTotalCost() {
        int totalCost = selectedSeats.size() * ticketPrice;
        if (insuranceCheckbox.isSelected()) {
            totalCost += selectedSeats.size() * insurancePrice;
        }
        totalCostLabel.setText("Total Cost: $" + totalCost);
    }
}