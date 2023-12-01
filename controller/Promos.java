import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import Controller.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class Promos {
    private Long promoID;
    private int discountPercent;
    private Date promoDateStart;
    private Date promoDateEnd;


    public Promos(Long promoID, int discountPercent, Date promoDateStart, Date promoDateEnd) {
        this.promoID = promoID;
        this.discountPercent = discountPercent;
        this.promoDateStart = promoDateStart;
        this.promoDateEnd = promoDateEnd;
    }


    public Map<Long, Promos> getPromosFromDB() {
        Map<Long, Promos> promoMap = new HashMap<>();
            if(DatabaseConnection.dbConnect!=null){
            String query = "SELECT promoID, discountPercent, promoDateStart, promoDateEnd FROM FLIGHTDB.PROMOS";
            
            try (PreparedStatement statement = DatabaseConnection.dbConnect.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long promoID = resultSet.getLong("promoID");
                    int discountPercent = resultSet.getInt("discountPercent");
                    Date promoDateStart = resultSet.getDate("promoDateStart");
                    Date promoDateEnd = resultSet.getDate("promoDateEnd");

                    Promos promo = new Promos(promoID, discountPercent, promoDateStart, promoDateEnd);
                    promoMap.put(promoID, promo);
                }
            
            
            } catch (SQLException e) {
                e.printStackTrace(); 
            }}

        return promoMap;
    }

    public Long getPromoID(){
        return promoID; 
    }

    public int getDiscountPrecent() {
        return discountPercent;
    }

    public Date getPromoDateStart() {
        return promoDateStart;
    }

    public Date getPromoDateEnd() {
        return promoDateEnd;
    }

    //     private void generatePromo() {
    //     try (Connection connection = dbConnection.getConnection()) {
    //         do {
    //             Random random = new Random();
    //             promoID = 1000 + random.nextInt(9000);
    //             discountPercent = 5 + random.nextInt(50);

    //             // Generate random promoDateStart and promoDateEnd within the last year
    //             promoDateStart = getRandomDateWithinLastYear();
    //             promoDateEnd = getRandomDateWithinLastYear();

    //         } while (promoIDExists(connection, promoID));
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private Date getRandomDateWithinLastMonthAndNextMonth() {
    //     Random random = new Random();
    //     Calendar calendar = new GregorianCalendar();

    //     // Set the end date to the last day of the next month
    //     calendar.add(Calendar.MONTH, 2); // Move to the next-next month
    //     calendar.set(Calendar.DAY_OF_MONTH, 1); // Set the day to the first day of the next month
    //     calendar.add(Calendar.DATE, -1); // Move to the last day of the next month
    //     Date endDate = calendar.getTime();

    //     // Set the start date to the first day of the last month
    //     calendar.setTime(endDate);
    //     calendar.add(Calendar.MONTH, -2); // Move to the last-last month
    //     calendar.set(Calendar.DAY_OF_MONTH, 1); // Set the day to the first day of the last month
    //     Date startDate = calendar.getTime();

    //     long startMillis = startDate.getTime();
    //     long endMillis = endDate.getTime();
    //     long randomMillisSinceEpoch = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));

    //     return new Date(randomMillisSinceEpoch);
    // }


    // private boolean promoIDExists(Connection connection, long promoID) throws SQLException {
    //     String query = "SELECT COUNT(*) FROM FLIGHTDB.PROMOS WHERE promoID = ?";
    //     try (PreparedStatement statement = connection.prepareStatement(query)) {
    //         statement.setLong(1, promoID);
    //         try (ResultSet resultSet = statement.executeQuery()) {
    //             if (resultSet.next()) {
    //                 return resultSet.getInt(1) > 0;
    //             }
    //         }
    //     }
    //     return false;
    // }
}