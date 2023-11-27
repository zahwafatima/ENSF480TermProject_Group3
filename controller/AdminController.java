package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DatabaseConnection;
import Entity.Aircraft;
import Entity.Crew;
import Entity.Date;
import Entity.Flight;

public class AdminController {
    

    // Browse the list of flights, their origin and destination in a specific date.
    public Map<String, Flight> browse_flights_spec(Date date){
        Map<String, Flight> flightsMap = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM FLIGHT WHERE departureDate = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                java.sql.Date sqlDate = new java.sql.Date(date.getYear(), date.getMonth(), date.getDay());
                preparedStatement.setDate(1, sqlDate);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Flight flight = createFlightFromResultSet(resultSet);
                        flightsMap.put(flight.getFlightNumber(), flight);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flightsMap;
    }

    public void browse_crew_spec(Crew crew){

    }

    public void browse_aircrafts(){

    }

    public void add_crew(Crew crew){

    }

    public void remove_crew(Crew crew){

    }

    public void add_aircraft(Aircraft aircraft){

    }

    public void remove_aircraft(Aircraft aircraft){

    }

    public void add_destination(){

    }

    public void remove_destination(){

    }

    public void add_flight_info(){

    }

    public void remove_flight_info(){

    }

    public void modify_flight_info(){

    }

    public void view_registered_users(){

    }
}
