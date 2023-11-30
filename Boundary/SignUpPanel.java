package Boundary;
import javax.swing.*;
import Entity.Address;
import Entity.User;
import Entity.Name;
import Controller.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SignUpPanel extends JPanel {
    private UserController usc;
    
    public SignUpPanel(UserController usc) {
        this.usc = usc;
        //usc = 
        
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // First Name
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("First Name:"), constraints);
        constraints.gridx = 1;
        JTextField firstNameField = new JTextField(15);
        add(firstNameField, constraints);

        // Last Name
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Last Name:"), constraints);
        constraints.gridx = 1;
        JTextField lastNameField = new JTextField(15);
        add(lastNameField, constraints);

        // Address Street
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Street:"), constraints);
        constraints.gridx = 1;
        JTextField streetField = new JTextField(15);
        add(streetField, constraints);

        // Address City
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("City:"), constraints);
        constraints.gridx = 1;
        JTextField cityField = new JTextField(15);
        add(cityField, constraints);

        // Address Postal Code
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Postal Code:"), constraints);
        constraints.gridx = 1;
        JTextField postalCodeField = new JTextField(15);
        add(postalCodeField, constraints);

        // Address Country
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Country:"), constraints);
        constraints.gridx = 1;
        JTextField countryField = new JTextField(15);
        add(countryField, constraints);

        // Phone Number
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Phone Number:"), constraints);
        constraints.gridx = 1;
        JTextField phoneNumberField = new JTextField(15);
        add(phoneNumberField, constraints);

        // Email
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Email:"), constraints);
        constraints.gridx = 1;
        JTextField emailField = new JTextField(15);
        add(emailField, constraints);

        // Password
        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Password:"), constraints);
        constraints.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        add(passwordField, constraints);

        // Checkbox for Registered User/Guest User
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 2;
        JCheckBox isRegisteredCheckbox = new JCheckBox("Register as a user (uncheck for guest)");
        
        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Here, you would handle the submission logic
            // For example, you could create a new User instance and save it to the database
            isRegisteredCheckbox.setSelected(true);
            add(isRegisteredCheckbox, constraints);
            boolean isRegistered = isRegisteredCheckbox.isSelected();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String city = cityField.getText();
            String country = countryField.getText();
            String postalCode = postalCodeField.getText();
            String street = streetField.getText();
            long phoneNumber = Long.parseLong(phoneNumberField.getText());
            String accessLevel = "user";
            Name name = new Name(firstName,lastName);
            Address addr = new Address(street, city, country);
            User user  = new User(isRegistered,name,addr, phoneNumber,email,password,accessLevel);
            addUsertoDB(user);
            System.out.print("testtt");
            // Create other components of User f    rom the fields...
            
            // Assuming you have a method to save this User to the database
            // saveUserToDatabase(newUser);
            JOptionPane.showMessageDialog(this, "Account created successfully.");
        });

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 1;
        add(submitButton, constraints);

        // Back to Login button
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            // Logic to switch back to the login panel
            // AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Login Card");
        });
        constraints.gridx = 1;
        add(backButton, constraints);
    }
    private void addUsertoDB(User user){
        
        usc.addUserToDB(user);
    }
}

// package Boundary;
// import javax.swing.JPanel;
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.util.HashMap;
// import java.util.Map;

// public class SignUpPanel extends JPanel {
//     public SignUpPanel(){
//         JPanel signUpPanel = new JPanel(new GridBagLayout());
//         GridBagConstraints constraints = new GridBagConstraints();
//         constraints.fill = GridBagConstraints.HORIZONTAL;
//         constraints.insets = new Insets(5, 5, 5, 5);
//         constraints.gridx = 0;
//         constraints.gridy = 0;
    
//         // First Name
//         signUpPanel.add(new JLabel("First Name:"), constraints);
//         constraints.gridx = 1;
//         JTextField firstNameField = new JTextField(15);
//         signUpPanel.add(firstNameField, constraints);
    
//         // Last Name
//         constraints.gridx = 0;
//         constraints.gridy = 1;
//         signUpPanel.add(new JLabel("Last Name:"), constraints);
//         constraints.gridx = 1;
//         JTextField lastNameField = new JTextField(15);
//         signUpPanel.add(lastNameField, constraints);
    
//         // Address
//         constraints.gridx = 0;
//         constraints.gridy = 2;
//         signUpPanel.add(new JLabel("Address:"), constraints);
//         constraints.gridx = 1;
//         JTextField addressField = new JTextField(15);
//         signUpPanel.add(addressField, constraints);
    
//         // Checkbox for Registered User/Guest User
//         constraints.gridx = 0;
//         constraints.gridy = 3;
//         constraints.gridwidth = 2;
//         JCheckBox isRegisteredCheckbox = new JCheckBox("Register as a user (uncheck for guest)");
//         isRegisteredCheckbox.setSelected(true);
//         signUpPanel.add(isRegisteredCheckbox, constraints);
    
//         // Submit button
//         JButton submitButton = new JButton("Submit");
//         submitButton.addActionListener(e -> {
//             // Here, you would handle the submission logic
//             // For example, you could check if the fields are filled and if the checkbox is checked
//             JOptionPane.showMessageDialog(signUpPanel, "Submission logic goes here.");
//         });
    
//         // Back to Login button
//         JButton backButton = new JButton("Back to Login");
//         backButton.addActionListener(e -> AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Login Card"));
    
//         // Adding buttons to the panel
//         constraints.gridx = 0;
//         constraints.gridy = 4;
//         constraints.gridwidth = 1;
//         signUpPanel.add(submitButton, constraints);
    
//         constraints.gridx = 1;
//         signUpPanel.add(backButton, constraints);
//     }
    

// }
