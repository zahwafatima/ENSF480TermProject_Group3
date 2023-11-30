import java.util.Map;

import Controller.DatabaseConnection;
import Entity.Address;
import Entity.Name;
import Entity.Ticket;
import Entity.User;


public class MemberUser extends User{
    
    private DatabaseConnection db; 

    public MemberUser(int userID, boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        super(userID, isRegistered, name, address, phoneNumber, email, pass, accessLevel);
        
        this.db = DatabaseConnection.getOnlyInstance();
    }

    @Override
    public int calculatePayment(Ticket ticket, int userEnteredPromoCode, Map<Long, Promo> promoMap) {
        int orgPrice = ticket.getPrice();
        int fullPrice = orgPrice; 

        // Check if the userEnteredPromoCode matches any promo in the promoMap
        Promo applicablePromo = promoMap.get((long) userEnteredPromoCode);

        if (applicablePromo != null) {
            // Check if the current time is within the promotional period
            Date currentDate = new Date(System.currentTimeMillis());
            if (currentDate.after(applicablePromo.getPromoDateStart()) && currentDate.before(applicablePromo.getPromoDateEnd())) {
                // Apply the discount to the original price
                fullPrice = (int) Math.round(orgPrice * (1 - applicablePromo.getDiscountPercent() / 100.0));
            }
        }

        return fullPrice;
    }






    




    
}
