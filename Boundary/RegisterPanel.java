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
                    System.out.println("hi lol");
                    

                    gsc.setUserRegistered(LoginPanel.username, LoginPanel.passwordString);

                    // Call subscribeToMembership
                    //guestUser.subscribeToMembership();
                }
            });

        
        

        // Add components to the panel
  
        add(registerButton);
        
        // Add other components as needed
    }

    // public void getUser(){
    //     GuestUser guestUser = gsc.getUserDetails(logpan.getUsername(), logpan.getPasswordString());
    // }
}


