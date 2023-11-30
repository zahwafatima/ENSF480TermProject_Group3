package Controller;
import java.util.ArrayList;

import Entity.User;


public class Login {
    private ArrayList<String> emailList; //CHANGE to save to database instead of list 
    private ArrayList<String> passwordList;
    private UserController userController;

    private static Login onlyInstance;

    private Login() {
        emailList = userController.emailList();
        passwordList = userController.passwordList();
    }

    public static Login getOnlyInstance() {
        if (onlyInstance == null) {
            onlyInstance = new Login();
        }
        return onlyInstance;
    }

    public void addUser(User user) {
        emailList.add(user.getEmail());
        passwordList.add(user.getPass());
    }

    public boolean isValidUser(String email, String password) { //CHANGE to save to database instead of list 
        int index = emailList.indexOf(email);
        if (index != -1) {
            // Email found, check the corresponding password
            return passwordList.get(index).equals(password);
        }
        return false; // Email not found
    }

    public ArrayList<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(ArrayList<String> emailList) {
        this.emailList = emailList;
    }

    public ArrayList<String> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(ArrayList<String> passwordList) {
        this.passwordList = passwordList;
    }

    public String getLastAddedEmail() {
        if (!emailList.isEmpty()) {
            return emailList.get(emailList.size() - 1);
        }
        return null; // Return null if the list is empty
    }

    public String getLastAddedPassword() {
        if (!passwordList.isEmpty()) {
            return passwordList.get(passwordList.size() - 1);
        }
        return null; // Return null if the list is empty
    }
}

