package Boundary;
import javax.swing.*;
import java.awt.*;
import Controller.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class LoginPanel extends JPanel {
    private GuestUser gsc == null;

    

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
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);
                System.out.println(username + "" + passwordString);
                String accessLevel = isValidUser(username, passwordString);
                System.out.println(accessLevel);

                if ( accessLevel.equals("noUser")) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);

                }else if(accessLevel.equals("customer") || accessLevel.equals("crew")){
                    JOptionPane.showMessageDialog(null, "Welcome User" );
                    AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "User Navigation Card");

                }else{
                    // accessLevel.equals("admin")
                    AirlineReservationSystem.cardLayout.show(AirlineReservationSystem.cardsPanel, "Admin Browse Flights Card");

                    JOptionPane.showMessageDialog(null, "Welcome Admin" );

                }
            }
        });

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

    private String isValidUser(String username,String passwordString){
        Login login = Login.getOnlyInstance();
        boolean userExists = login.isValidUser(username,passwordString);
        if(userExists){
            
            String accessLevel = login.getUserAccessLevel(username, passwordString);
            return accessLevel;

        }else{
            return "noUser";
        }
        

    }


}
