import Entity.Address;
import Entity.Name;
import Entity.Ticket;
import Entity.User;

public class GuestUser extends User{

    public GuestUser(boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        super(isRegistered, name, address, phoneNumber, email, pass, accessLevel);
    }

    @Override
    public int calculatePayment(Ticket ticket) {
        // Custom calculation for guest users
        
        return ticket.getPrice(); // 10% discount for guest users
    }
}

