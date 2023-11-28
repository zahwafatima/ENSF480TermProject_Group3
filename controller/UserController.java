package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Entity.Address;
import Entity.Aircraft;
import Entity.Airline;
import Entity.Crew;
import Entity.Date;
import Entity.Flight;
import Entity.Location;
import Entity.Name; 
import Entity.User;
import DatabaseConnection;

public class UserController{

    private DatabaseConection db;

    public userController(DatabaseConection db){
        this.db = db;
        


    }

    public Map<String, Seat> browssSeatMap(){

    }


}