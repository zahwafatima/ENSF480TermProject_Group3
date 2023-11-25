DROP DATABASE IF EXISTS FLIGHTDB;
CREATE DATABASE FLIGHTDB; 
USE FLIGHTDB;

CREATE TABLE FLIGHT (
    flightNumber VARCHAR(5) NOT NULL,
    airline VARCHAR(20) NOT NULL,
    destination VARCHAR(20) NOT NULL,
    origin VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    departureDate DATE,
    arrivalDate DATE,
    crewList VARCHAR(1000),
    PRIMARY KEY (flightNumber)
);

CREATE TABLE USERS (
    userID INT AUTO_INCREMENT,
    isRegistered BOOLEAN, 
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    street VARCHAR(100),
    city VARCHAR(50),
    country VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(100),
    PRIMARY KEY (userID)
);

CREATE TABLE ADDRESS (
    street VARCHAR(100),
    city VARCHAR(50),
    country VARCHAR(50),
    userID INT,
    PRIMARY KEY (street, city, country),
    FOREIGN KEY (userID) REFERENCES USERS(userID)
);

CREATE TABLE CREW (
    crewID INT AUTO_INCREMENT,
    pilot VARCHAR(50),
    copilot VARCHAR(50),
    flightAttendant1 VARCHAR(50),
    flightAttendant2 VARCHAR(50),
    flightAttendant3 VARCHAR(50),
    flightAttendant4 VARCHAR(50),
    PRIMARY KEY (crewID)
);

-- Inserting data into USERS table
INSERT INTO USERS (isRegistered, firstName, lastName, street, city, country, email, password)
VALUES
(1, 'John', 'Smith', '123 Main St', 'New York', 'USA', 'email@email.com', '12345'),
(1, 'Jane', 'Doe', '456 Main St', 'New York', 'USA', 'aaa@email.com', 'password'),
(1, 'Your', 'Mom', '789 Main St', 'New York', 'USA', 'bbbb@email.com', '1111');

-- Inserting data into ADDRESS table
INSERT INTO ADDRESS (street, city, country, userID)
VALUES
('123 Main St', 'New York', 'USA', 1),
('456 Main St', 'New York', 'USA', 2),
('789 Main St', 'New York', 'USA', 3);

-- Inserting flights
INSERT INTO FLIGHT (flightNumber, airline, destination, origin, capacity, departureDate, arrivalDate)
VALUES
('FL001', 'Delta', 'London', 'New York', 200, '2023-12-01', '2023-12-02'),
('FL002', 'United', 'Paris', 'Los Angeles', 180, '2023-12-05', '2023-12-06'),
('FL003', 'Lufthansa', 'Tokyo', 'Berlin', 220, '2023-12-10', '2023-12-11'),
('FL004', 'Emirates', 'Dubai', 'New York', 250, '2023-12-15', '2023-12-16'),
('FL005', 'British Airways', 'Rome', 'London', 190, '2023-12-20', '2023-12-21'),
('FL006', 'Qatar Airways', 'Sydney', 'Tokyo', 230, '2023-12-25', '2023-12-26');


-- Inserting CREW data, 6 crew members per flight
INSERT INTO CREW (pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4)
VALUES
('Harry Potter', 'Ron Weasley', 'Hermione Granger', 'Ginny Weasley', 'Luna Lovegood', 'Neville Longbottom'),
('Jack Sparrow', 'Will Turner', 'Elizabeth Swann', 'Joshamee Gibbs', 'Hector Barbossa', 'James Norrington'),
('Luke Skywalker', 'Han Solo', 'Leia Organa', 'Chewbacca', 'Obi-Wan Kenobi', 'Yoda'),
('Frodo Baggins', 'Samwise Gamgee', 'Gandalf', 'Aragorn', 'Legolas', 'Gimli'),
('Tony Stark', 'Steve Rogers', 'Natasha Romanoff', 'Bruce Banner', 'Clint Barton', 'Thor');

ALTER TABLE FLIGHT
ADD COLUMN crewID INT,
ADD FOREIGN KEY (crewID) REFERENCES CREW(crewID);

UPDATE FLIGHT SET crewID = 1 WHERE flightNumber = 'FL001';
-- Assign other crew members to corresponding flights...

SELECT f.flightNumber, f.airline, c.pilot, c.copilot, c.flightAttendant1, c.flightAttendant2, c.flightAttendant3, c.flightAttendant4
FROM FLIGHT f
JOIN CREW c ON f.crewID = c.crewID;

-- SELECT * FROM FLIGHT;

