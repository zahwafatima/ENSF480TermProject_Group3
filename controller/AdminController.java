package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.Aircraft;
import Entity.Crew;
import Entity.Date;
import Entity.Flight;

import DatabaseConnection;

public class AdminController {
    
    // Browse the list of flights, their origin and destination in a specific date.
    public void browse_flights_spec(Date date){
        try (DatabaseConnection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM FLIGHT WHERE departureDate = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                java.sql.Date sqlDate = new java.sql.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDay());
                preparedStatement.setDate(1, sqlDate);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    displayFlightsInPopup(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
