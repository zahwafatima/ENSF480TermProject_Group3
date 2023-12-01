import java.time.LocalDate;
import java.util.Map;

import Controller.DatabaseConnection;
import Entity.Address;
import Entity.Name;
import Entity.Ticket;
import Entity.User;


public class MemberUser extends User{
    
    private DatabaseConnection db; 

    public MemberUser(boolean isRegistered, Name name, Address address, long phoneNumber, String email, String pass, String accessLevel) {
        super(isRegistered, name, address, phoneNumber, email, pass, accessLevel);
        
        this.db = DatabaseConnection.getOnlyInstance();
    }

    // @Override
    // public int calculatePayment(Ticket ticket, int userEnteredPromoCode, Map<Long, Promo> promoMap) {
    //     int orgPrice = ticket.getPrice();
    //     int fullPrice = orgPrice; 

    //     // Check if the userEnteredPromoCode matches any promo in the promoMap
    //     Promos applicablePromo = promoMap.get((long) userEnteredPromoCode);

    //     if (applicablePromo != null) {
    //         // Get the current date
    //         LocalDate currentDate = LocalDate.now();

    //         // Parse promo start and end dates to LocalDate
    //         LocalDate promoStartDate = LocalDate.parse(applicablePromo.getPromoDateStart());
    //         LocalDate promoEndDate = LocalDate.parse(applicablePromo.getPromoDateEnd());

    //         // Apply the discount if the current date is within the promotional period
    //         if (currentDate.isAfter(promoStartDate) && currentDate.isBefore(promoEndDate)) {
    //             fullPrice = (int) Math.round(orgPrice * (1 - applicablePromo.getDiscountPercent() / 100.0));
    //         }
    //     }

    //     return fullPrice;
    // }
    
}
