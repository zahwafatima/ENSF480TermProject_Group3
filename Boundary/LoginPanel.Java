package Boundary;
import javax.swing.*;
import java.awt.*;
import Controller.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class LoginPanel extends JPanel {
    

    public LoginPanel() {
        

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        JTextField userTextField = new JTextField(25);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        // Login button
        JButton loginButton = new JButton("nogin");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);
                System.out.println(username + "" + passwordString);
                if (isValidUser(username, passwordString)== true) {
                    JOptionPane.showMessageDialog(null, "Welcome" );
                    AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Browse Flights Card");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
                }});
        //  {
        
        
            
        //         // If valid, show welcome message
        //         JOptionPane.showMessageDialog(null, "Welcome, " + username + "!");
        //         // Navigate to the next card
        //         //AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Browse Flights Card");
        //     } else {
        //         // If not valid, show error message
        //         JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        //     }
        // }
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
    private boolean isValidUser(String username,String passwordString){
        Login login = Login.getOnlyInstance();
        //login.printUsernameList(login.getEmailList());
        //System.out.println("user");
         return login.isValidUser(username,passwordString);
    }
    // private String isValidUser(String username,String passwordString){
    //     Login login = Login.getOnlyInstance();
    //     login.isValidUser(username,passwordString);
    // }


}
