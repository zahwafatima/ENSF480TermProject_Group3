import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class SignUpPanel extends Jpanel {
    public SignupPanel(){
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
    
        // First Name
        signUpPanel.add(new JLabel("First Name:"), constraints);
        constraints.gridx = 1;
        JTextField firstNameField = new JTextField(15);
        signUpPanel.add(firstNameField, constraints);
    
        // Last Name
        constraints.gridx = 0;
        constraints.gridy = 1;
        signUpPanel.add(new JLabel("Last Name:"), constraints);
        constraints.gridx = 1;
        JTextField lastNameField = new JTextField(15);
        signUpPanel.add(lastNameField, constraints);
    
        // Address
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(new JLabel("Address:"), constraints);
        constraints.gridx = 1;
        JTextField addressField = new JTextField(15);
        signUpPanel.add(addressField, constraints);
    
        // Checkbox for Registered User/Guest User
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        JCheckBox isRegisteredCheckbox = new JCheckBox("Register as a user (uncheck for guest)");
        isRegisteredCheckbox.setSelected(true);
        signUpPanel.add(isRegisteredCheckbox, constraints);
    
        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Here, you would handle the submission logic
            // For example, you could check if the fields are filled and if the checkbox is checked
            JOptionPane.showMessageDialog(signUpPanel, "Submission logic goes here.");
        });
    
        // Back to Login button
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> cardLayout.show(cardsPanel, LOGIN_CARD));
    
        // Adding buttons to the panel
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        signUpPanel.add(submitButton, constraints);
    
        constraints.gridx = 1;
        signUpPanel.add(backButton, constraints);
    }
    

}
