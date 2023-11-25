DROP DATABASE IF EXISTS FLIGHTDB;
CREATE DATABASE FLIGHTDB; 
USE FLIGHTDB;

-- airline in flight is not properly implemented yet***
-- will update data better later***
-- flight.crewList is being annoying***

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
    crewID VARCHAR(7) NOT NULL,
    pilot VARCHAR(50),
    copilot VARCHAR(50),
    flightAttendant1 VARCHAR(50),
    flightAttendant2 VARCHAR(50),
    flightAttendant3 VARCHAR(50),
    flightAttendant4 VARCHAR(50),
    PRIMARY KEY (crewID)
);

CREATE TABLE SEAT (
	seatNumber VARCHAR(3) NOT NULL,
    seatClass VARCHAR(50) NOT NULL,
    isBooked BOOLEAN,
    price DECIMAL(10,2),
    PRIMARY KEY (seatNumber)
);

CREATE TABLE TICKET (
    ticketNumber VARCHAR(5) NOT NULL,
    flightNumber VARCHAR(5) NOT NULL,
    passenger_fName VARCHAR(50),
    passenger_lName VARCHAR(50),
    seatNumber VARCHAR(3),
    class VARCHAR(50),
    userID INT,
    PRIMARY KEY (ticketNumber),
    FOREIGN KEY (flightNumber) REFERENCES FLIGHT(flightNumber),
    FOREIGN KEY (userID) REFERENCES USERS(userID),
    FOREIGN KEY (seatNumber) REFERENCES SEAT(seatNumber)
);


-- Inserting data into USERS table
INSERT INTO USERS (isRegistered, firstName, lastName, street, city, country, email, password)
VALUES
(1, 'John', 'Smith', '123 Main St', 'New York', 'USA', 'email@email.com', '12345'),
(1, 'Jane', 'Doe', '456 Main St', 'New York', 'USA', 'aaa@email.com', 'password'),
(1, 'Your', 'Mom', '789 Main St', 'New York', 'USA', 'bbbb@email.com', '1111'),
(0, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(0, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

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
INSERT INTO CREW (crewID, pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4)
VALUES
('C001', 'Harry Potter', 'Ron Weasley', 'Hermione Granger', 'Ginny Weasley', 'Luna Lovegood', 'Neville Longbottom'),
('C002', 'Jack Sparrow', 'Will Turner', 'Elizabeth Swann', 'Joshamee Gibbs', 'Hector Barbossa', 'James Norrington'),
('C003', 'Luke Skywalker', 'Han Solo', 'Leia Organa', 'Chewbacca', 'Obi-Wan Kenobi', 'Yoda'),
('C004', 'Frodo Baggins', 'Samwise Gamgee', 'Gandalf', 'Aragorn', 'Legolas', 'Gimli'),
('C005', 'Tony Stark', 'Steve Rogers', 'Natasha Romanoff', 'Bruce Banner', 'Clint Barton', 'Thor');


-- Inserting SEAT data - 30 seats per flight, 10 first class, 20 economy, 10 seats per row
INSERT INTO SEAT (seatNumber, seatClass, isBooked, price)
VALUES
('1A', 'First', 0, 1000.00),
('1B', 'First', 0, 1000.00),
('1C', 'First', 0, 1000.00),
('2A', 'First', 0, 1000.00),
('2B', 'First', 0, 1000.00),
('2C', 'First', 0, 1000.00),
('3A', 'First', 0, 1000.00),
('3B', 'First', 0, 1000.00),
('3C', 'First', 0, 1000.00),
('4A', 'First', 0, 1000.00),
('4B', 'First', 0, 1000.00),
('4C', 'First', 0, 1000.00),
('5A', 'First', 0, 1000.00),
('5B', 'First', 0, 1000.00),
('5C', 'First', 0, 1000.00),
('6A', 'First', 0, 1000.00),
('6B', 'First', 0, 1000.00),
('6C', 'First', 0, 1000.00),
('7A', 'First', 0, 1000.00),
('7B', 'First', 0, 1000.00),
('7C', 'First', 0, 1000.00),
('8A', 'First', 0, 1000.00),
('8B', 'First', 0, 1000.00),
('8C', 'First', 0, 1000.00),
('9A', 'First', 0, 1000.00),
('9B', 'First', 0, 1000.00),
('9C', 'First', 0, 1000.00),
('10A', 'First', 0, 1000.00),
('10B', 'First', 0, 1000.00),
('10C', 'First', 0, 1000.00),
('11A', 'Economy', 0, 500.00),
('11B', 'Economy', 0, 500.00),
('11C', 'Economy', 0, 500.00),
('12A', 'Economy', 0, 500.00),
('12B', 'Economy', 0, 500.00),
('12C', 'Economy', 0, 500.00),
('13A', 'Economy', 0, 500.00),
('13B', 'Economy', 0, 500.00),
('13C', 'Economy', 0, 500.00),
('14A', 'Economy', 0, 500.00),
('14B', 'Economy', 0, 500.00),
('14C', 'Economy', 0, 500.00),
('15A', 'Economy', 0, 500.00),
('15B', 'Economy', 0, 500.00),
('15C', 'Economy', 0, 500.00),
('16A', 'Economy', 0, 500.00),
('16B', 'Economy', 0, 500.00),
('16C', 'Economy', 0, 500.00),
('17A', 'Economy', 0, 500.00),
('17B', 'Economy', 0, 500.00),
('17C', 'Economy', 0, 500.00),
('18A', 'Economy', 0, 500.00),
('18B', 'Economy', 0, 500.00),
('18C', 'Economy', 0, 500.00),
('19A', 'Economy', 0, 500.00),
('19B', 'Economy', 0, 500.00),
('19C', 'Economy', 0, 500.00),
('20A', 'Economy', 0, 500.00),
('20B', 'Economy', 0, 500.00),
('20C', 'Economy', 0, 500.00),
('21A', 'Economy', 0, 500.00),
('21B', 'Economy', 0, 500.00),
('21C', 'Economy', 0, 500.00),
('22A', 'Economy', 0, 500.00),
('22B', 'Economy', 0, 500.00),
('22C', 'Economy', 0, 500.00),
('23A', 'Economy', 0, 500.00),
('23B', 'Economy', 0, 500.00),
('23C', 'Economy', 0, 500.00),
('24A', 'Economy', 0, 500.00),
('24B', 'Economy', 0, 500.00),
('24C', 'Economy', 0, 500.00),
('25A', 'Economy', 0, 500.00),
('25B', 'Economy', 0, 500.00),
('25C', 'Economy', 0, 500.00),
('26A', 'Economy', 0, 500.00),
('26B', 'Economy', 0, 500.00),
('26C', 'Economy', 0, 500.00),
('27A', 'Economy', 0, 500.00),
('27B', 'Economy', 0, 500.00),
('27C', 'Economy', 0, 500.00),
('28A', 'Economy', 0, 500.00),
('28B', 'Economy', 0, 500.00),
('28C', 'Economy', 0, 500.00),
('29A', 'Economy', 0, 500.00),
('29B', 'Economy', 0, 500.00),
('29C', 'Economy', 0, 500.00),
('30A', 'Economy', 0, 500.00),
('30B', 'Economy', 0, 500.00),
('30C', 'Economy', 0, 500.00);

-- Inserting TICKET data
INSERT INTO TICKET (ticketNumber, flightNumber, passenger_fName, passenger_lName, seatNumber, class, userID)
VALUES
('T001', 'FL001', 'John', 'Smith', '1A', 'First', 1),
('T002', 'FL001', 'Jane', 'Doe', '1B', 'First', 2),
('T003', 'FL001', 'Your', 'Mom', '1C', 'First', 3),
('T004', 'FL001', NULL, NULL, NULL, NULL, 4),
('T005', 'FL001', NULL, NULL, NULL, NULL, 5),
('T006', 'FL001', NULL, NULL, NULL, NULL, 6),
('T007', 'FL002', 'John', 'Smith', '2A', 'First', 1),
('T008', 'FL002', 'Jane', 'Doe', '2B', 'First', 2),
('T009', 'FL002', 'Your', 'Mom', '2C', 'First', 3),
('T010', 'FL002', NULL, NULL, NULL, NULL, 4),
('T011', 'FL002', NULL, NULL, NULL, NULL, 5),
('T012', 'FL002', NULL, NULL, NULL, NULL, 6),
('T013', 'FL003', 'John', 'Smith', '3A', 'First', 1),
('T014', 'FL003', 'Jane', 'Doe', '3B', 'First', 2),
('T015', 'FL003', 'Your', 'Mom', '3C', 'First', 3),
('T016', 'FL003', NULL, NULL, NULL, NULL, 4),
('T017', 'FL003', NULL, NULL, NULL, NULL, 5),
('T018', 'FL003', NULL, NULL, NULL, NULL, 6),
('T019', 'FL004', 'John', 'Smith', '4A', 'First', 1),
('T020', 'FL004', 'Jane', 'Doe', '4B', 'First', 2),
('T021', 'FL004', 'Your', 'Mom', '4C', 'First', 3),
('T022', 'FL004', NULL, NULL, NULL, NULL, 4);

ALTER TABLE FLIGHT
ADD COLUMN crewID VARCHAR(7),
ADD FOREIGN KEY (crewID) REFERENCES CREW(crewID);

UPDATE FLIGHT SET crewID = 'C001' WHERE flightNumber = 'FL001';
-- Assign other crew members to corresponding flights...

-- displays properly rn
-- SELECT f.flightNumber, f.airline, c.pilot, c.copilot, c.flightAttendant1, c.flightAttendant2, c.flightAttendant3, c.flightAttendant4
-- FROM FLIGHT f
-- JOIN CREW c ON f.crewID = c.crewID;

SELECT * FROM TICKET;

-- SELECT * FROM CREW;

-- SELECT * FROM TICKET;
