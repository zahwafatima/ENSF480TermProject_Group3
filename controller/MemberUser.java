import Controller.DatabaseConnection;
import Entity.Address;
import Entity.Name;
import Entity.User;



public class MemberUser extends User{
    
    private DatabaseConnection db; 

    public MemberUser(int userID, boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        super(isRegistered, name, address, phoneNumber, email, pass, accessLevel);
        
        this.db = DatabaseConnection.getOnlyInstance();
        addUserToDB(this);
    }


    

    




    
}
