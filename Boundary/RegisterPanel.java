package Boundary;

import javax.swing.*;

import Boundary.AirlineReservationSystem;
import Boundary.LoginPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.GuestUser;
import Controller.UserController;

public class RegisterPanel extends JPanel {
    private UserController gsc;
    private GuestUser guestUser;
    private LoginPanel logpan;

    public RegisterPanel() {
        this.gsc = new UserController();
        //this.logpan =  AirlineReservationSystem.getOnlyInstance().getLoginPanel();
        System.out.println("dascsdc");



        JButton registerButton = null;  // Declare outside the if block

            registerButton = new JButton("Register for Membership");
            registerButton.addActionListener(new ActionListener() {
        
                @Override
                public void actionPerformed(ActionEvent e) {                    

                    gsc.setUserRegistered(LoginPanel.username, LoginPanel.passwordString);
                    JOptionPane.showMessageDialog(RegisterPanel.this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            });

          
        add(registerButton);
        
    }

}


