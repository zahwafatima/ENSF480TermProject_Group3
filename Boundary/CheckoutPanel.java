package Boundary;
import javax.swing.*;

import Boundary.BrowseUserFlightsPanel;
import Boundary.SeatMapPanel;
import Controller.UserController;

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
    private UserController usc;
    private JTextField cardNumberField, expiryDateField,cvvField, promoIDField;
    private int userID;
    private int discPercentage;

    public CheckoutPanel(UserController userCon) {
        this.usc = userCon;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Credit Card Number Field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField(24);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cardNumberLabel, gbc);
        gbc.gridx = 1;
        add(cardNumberField, gbc);

        // Expiry Date Field
        JLabel expiryDateLabel = new JLabel("Expiry Date (MM/YY):");
        expiryDateField = new JTextField(4);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(expiryDateLabel, gbc);
        gbc.gridx = 1;
        add(expiryDateField, gbc);

        // CVV Field
        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JTextField(3);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(cvvLabel, gbc);
        gbc.gridx = 1;
        add(cvvField, gbc);

        JLabel promoIDLabel= new JLabel("Enter promo ID:");
        promoIDField = new JTextField(3);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(promoIDLabel, gbc);
        gbc.gridx = 1;
        add(promoIDField, gbc);
        insuranceCheckbox = new JCheckBox("Add Ticket Insurance ($20 per seat)");
        gbc.gridx = 0;
        gbc.gridy = 4;
        insuranceCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int promoID = Integer.parseInt(promoIDField.getText());
                 discPercentage = usc.getDiscountPercentage(promoID);
                System.out.println(discPercentage);
               int costtemp = updateTotalCost(discPercentage);
            }
        });
        add(insuranceCheckbox, gbc);

        
        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate total amount here
               // double totalAmount =20 ; // Replace this with your actual calculation
                int totalAmount = updateTotalCost(discPercentage);
                // Create a custom panel for the dialog
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BorderLayout());
                dialogPanel.add(new JLabel("Total Amount: $" + totalAmount), BorderLayout.NORTH);
        
                // Create a Purchase button
                JButton purchaseButton = new JButton("Purchase");
                purchaseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String cardNumber = cardNumberField.getText();
                String expiryDate = expiryDateField.getText();
                String cvv = cvvField.getText();
                
                        /// 1111111111111111
                if (!cardNumber.matches("\\d{16}")) {
                    JOptionPane.showMessageDialog(CheckoutPanel.this,"Invalid Card Number. It must be 16 digits long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    //
                if (!expiryDate.matches("\\d{4}")) { // Matches MMYY format where MM is 01/12
                    JOptionPane.showMessageDialog(CheckoutPanel.this,"Invalid Expiry Date. Format must be MMYY.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!cvv.matches("\\d{3}")) {
                    JOptionPane.showMessageDialog(CheckoutPanel.this,"Invalid CVV. It must be 3 digits long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usc.generateTicket(SeatMapPanel.seatString, BrowseUserFlightsPanel.flightSelected);
               // userID = usc.getUserID(LoginPanel.username, LoginPanel.passwordString);
                //get a price for the ticket

                // usc.generateTicket(SeatMapPanel.seatString, BrowseUserFlightsPanel.flightSelected,userID);
                        String receipt = "Ticket for " + LoginPanel.username + "/n" + "Flight : "+ BrowseUserFlightsPanel.flightSelected+"/n"+"Seat Number: " + SeatMapPanel.seatString;
                         JOptionPane.showMessageDialog(null, "Purchase completed for! Here is your iternary "+"/n"+ receipt);
                    };
                });
                dialogPanel.add(purchaseButton, BorderLayout.SOUTH);
        
                // Show the dialog
                JOptionPane.showConfirmDialog(null, dialogPanel, "Total Amount", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                //usc.generateTicket(SeatMapPanel.seatString, BrowseUserFlightsPanel.flightSelected,userID);
            }
        });
        add(calculateButton);

               
    }
    private int updateTotalCost(int discountp){
        int totalCost =  ticketPrice;
        totalCost = (int) Math.round(ticketPrice * (1 - (discountp / 100.0)));
        if (insuranceCheckbox.isSelected()) {
            totalCost +=  insurancePrice;
        }
        return totalCost;
    }
    }
    //     this.selectedSeats = selectedSeats;
    //     setLayout(new BorderLayout());

    //     // Insurance option
    //     insuranceCheckbox = new JCheckBox("Add Ticket Insurance ($20 per seat)");
    //     insuranceCheckbox.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             updateTotalCost();
    //         }
    //     });

    //     // Total cost label
    //     totalCostLabel = new JLabel();
    //     updateTotalCost(); // Update initial cost

    //     // Confirm button
    //     JButton confirmButton = new JButton("Confirm Booking");
    //     confirmButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             // Logic to handle booking confirmation
    //             JOptionPane.showMessageDialog(CheckoutPanel.this, "Booking Confirmed!");
    //         }
    //     });

    //     add(insuranceCheckbox, BorderLayout.NORTH);
    //     add(totalCostLabel, BorderLayout.CENTER);
    //     add(confirmButton, BorderLayout.SOUTH);
    // }

    // private void updateTotalCost() {
    //     int totalCost = selectedSeats.size() * ticketPrice;
    //     if (insuranceCheckbox.isSelected()) {
    //         totalCost += selectedSeats.size() * insurancePrice;
    //     }
    //     totalCostLabel.setText("Total Cost: $" + totalCost);
    // }
// });
//     }
// }