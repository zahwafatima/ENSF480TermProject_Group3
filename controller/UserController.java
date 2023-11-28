package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Entity.Seat;
import DatabaseConnection;

public class UserController {

    private DatabaseConnection db;

    public UserController(DatabaseConnection db) {
        this.db = db;
    }

    public Map<String, Seat> browseSeatMap() {
        Map<String, Seat> seatMap = new HashMap<>();

        try (Connection connection = db.getConnection()) {
            String sql = "SELECT * FROM FLIGHTDB.SEATS";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Assuming Seat class has a constructor that takes relevant parameters
                        Seat seat = new Seat(
                                resultSet.getString("seatNumber"),
                                resultSet.getString("seatClass"),
                                resultSet.getBoolean("isBooked"),
                                resultSet.getDouble("price")
                        );
                        seatMap.put(seat.getSeatNumber(), seat);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatMap;
    }
}
