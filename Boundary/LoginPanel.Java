package Boundary;
import javax.swing.*;
import java.awt.*;

 class LoginPanel extends JPanel {
    

    public LoginPanel() {
        

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        JTextField userTextField = new JTextField(15);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Browse Flights Card"));

        // Adding components to the panel with constraints
        addComponent(this, constraints, userLabel, 0, 0);
        addComponent(this, constraints, userTextField, 1, 0);
        addComponent(this, constraints, passwordLabel, 0, 1);
        addComponent(this, constraints, passwordField, 1, 1);
        addComponent(this, constraints, loginButton, 0, 2, 2, 1);

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Sign-Up Card"));
        addComponent(this, constraints, signUpButton, 0, 3, 2, 1);

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login Panel"));
    }

    private void addComponent(JPanel panel, GridBagConstraints constraints, 
                              Component component, int x, int y, int width, int height) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        panel.add(component, constraints);
    }

    private void addComponent(JPanel panel, GridBagConstraints constraints, 
                              Component component, int x, int y) {
        addComponent(panel, constraints, component, x, y, 1, 1);
    }
}
