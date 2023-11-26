package Controller;
import java.util.ArrayList;

public class Login {
    private ArrayList<String> emailList; //CHANGE to save to database instead of list 
    private ArrayList<String> passwordList;

    private static Login onlyInstance;

    private Login() {
        emailList = new ArrayList<>();
        passwordList = new ArrayList<>();
    }

    public static Login getOnlyInstance() {
        if (onlyInstance == null) {
            onlyInstance = new Login();
        }
        return onlyInstance;
    }

    public void addUser(String email, String password) {
        emailList.add(email); //CHANGE to save to database instead of list 
        passwordList.add(password);
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
}

