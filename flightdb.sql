DROP DATABASE IF EXISTS FLIGHTDB;
CREATE DATABASE FLIGHTDB; 
USE FLIGHTDB;

CREATE TABLE AIRLINE (
    airlineID INT AUTO_INCREMENT,
    airline_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (airlineID)
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
    pass VARCHAR(100),
    phoneNumber VARCHAR(100),
    accessLevel VARCHAR(100),
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
    crewID VARCHAR(10) NOT NULL,
    pilot INT,
    copilot INT,
    flightAttendant1 INT,
    flightAttendant2 INT,
    flightAttendant3 INT,
    flightAttendant4 INT,
    PRIMARY KEY (crewID),
    FOREIGN KEY (pilot) REFERENCES USERS(userID),
    FOREIGN KEY (copilot) REFERENCES USERS(userID),
    FOREIGN KEY (flightAttendant1) REFERENCES USERS(userID),
    FOREIGN KEY (flightAttendant2) REFERENCES USERS(userID),
    FOREIGN KEY (flightAttendant3) REFERENCES USERS(userID),
    FOREIGN KEY (flightAttendant4) REFERENCES USERS(userID)
);

CREATE TABLE DESTINATION (
	destinationID INT,
    destinationCountry VARCHAR(50) NOT NULL,
    destinationCity VARCHAR(50) NOT NULL,
    PRIMARY KEY (destinationID)
);

CREATE TABLE AIRCRAFT (
    aircraftID INT,
    aircraftModel VARCHAR(20) NOT NULL,
    capacity INT,
    PRIMARY KEY (aircraftID)
    #FOREIGN KEY (airline_name) REFERENCES AIRLINE(airline_name)
);

CREATE TABLE FLIGHT (
    flightNumber VARCHAR(5) NOT NULL,
    crewID VARCHAR(10) NOT NULL,
    destination_country VARCHAR(20) NOT NULL,
    destination_city VARCHAR(20) NOT NULL,
    origin_country VARCHAR(20) NOT NULL,
    origin_city VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    departureDate VARCHAR(20) NOT NULL,
    arrivalDate VARCHAR(20) NOT NULL,
    aircraftID INT, 
    aircraftModel VARCHAR(20) NOT NULL, 
    
    PRIMARY KEY (flightNumber),
    FOREIGN KEY (crewID) REFERENCES CREW(crewID),
    FOREIGN KEY (aircraftID) REFERENCES AIRCRAFT(aircraftID)
);

CREATE TABLE SEAT (
	seatID INT auto_increment,
    seatNumber VARCHAR(3) NOT NULL,
    flightNumber VARCHAR(5) NOT NULL,
    seatClass VARCHAR(50) NOT NULL,
    isBooked BOOLEAN,
    PRIMARY KEY (seatID),
    INDEX idx_seatNumber (seatNumber),
    INDEX idx_seatClass (seatClass),
    FOREIGN KEY (flightNumber) REFERENCES FLIGHT(flightNumber)
);

CREATE TABLE TICKET (
    ticketNumber VARCHAR(5) NOT NULL,
    flightNumber VARCHAR(5) NOT NULL,
    passenger_fName VARCHAR(50),
    passenger_lName VARCHAR(50),
    seatNumber VARCHAR(3) NOT NULL,
    seatClass VARCHAR(50) NOT NULL,
    userID INT,
    PRIMARY KEY (ticketNumber),
    FOREIGN KEY (flightNumber) REFERENCES FLIGHT(flightNumber),
    FOREIGN KEY (seatNumber) REFERENCES SEAT(seatNumber),
    FOREIGN KEY (seatClass) REFERENCES SEAT(seatClass),
    FOREIGN KEY (userID) REFERENCES USERS(userID)
);




-- Inserting data into USERS table
INSERT INTO USERS (userID, isRegistered, firstName, lastName, street, city, country, email, pass, phoneNumber, accessLevel)
VALUES
(1, true, 'John', 'Doe', '123 Main St', 'Cityville', 'Countryland', 'john.doe@example.com', 'password123', 1234567890, "customer"),
(2, true, 'Jane', 'Smith', '456 Oak St', 'Townsville', 'Countryland', 'jane.smith@example.com', 'pass456', 9876543210, "customer"),
(3, true, 'Mike', 'Johnson', '789 Pine St', 'Villagetown', 'Countryland', 'alice.johnson@example.com', 'secretPass', 5551234567, "customer"),
(4, false, 'Emily', 'Williams', '101 Maple St', 'Cityville', 'Countryland', 'bob.brown@example.com', 'password987', 3216549870, "customer"),
(5, false, 'Chris', 'Brown', '202 Elm St', 'Townsville', 'Countryland', 'eva.miller@example.com', 'pass789', 7894561230, "customer"),
(6, true, 'Sophia', 'Taylor', '303 Birch St', 'Villagetown', 'Countryland', 'michael.davis@example.com', 'myPassword', 6549873210, "customer"),
(7, false, 'Daniel', 'Miller', '404 Cedar St', 'Cityville', 'Countryland', 'sophia.anderson@example.com', 'securePass', 4567890123, "customer"),
(8, false, 'Olivia', 'Anderson', '505 Pine St', 'Townsville', 'Countryland', 'daniel.white@example.com', 'danielPass', 7890123456, "customer"),
(9, true, 'Aiden', 'White', '606 Oak St', 'Villagetown', 'Countryland', 'olivia.moore@example.com', 'oliviaPass', 1597534680, "customer"),
(10, false, 'James', 'Taylor', '707 Birch St', 'Cityville', 'Countryland', 'james.taylor@example.com', 'james123', 3571592468, "admin"),
(11, false, 'Alice', 'Johnson', '123 Main St', 'Cityville', 'Countryland', 'alice.johnson@example.com', 'crewPass1', 1111111111, 'crew'),
(12, false, 'Bob', 'Smith', '456 Oak St', 'Townsville', 'Countryland', 'bob.smith@example.com', 'crewPass2', 2222222222, 'crew'),
(13, false, 'Charlie', 'Brown', '789 Pine St', 'Villagetown', 'Countryland', 'charlie.brown@example.com', 'crewPass3', 3333333333, 'crew'),
(14, false, 'David', 'Miller', '101 Maple St', 'Cityville', 'Countryland', 'david.miller@example.com', 'crewPass4', 4444444444, 'crew'),
(15, false, 'Eva', 'Williams', '202 Elm St', 'Townsville', 'Countryland', 'eva.williams@example.com', 'crewPass5', 5555555555, 'crew'),
(16, false, 'Grace', 'Anderson', '303 Birch St', 'Villagetown', 'Countryland', 'grace.anderson@example.com', 'crewPass6', 6666666666, 'crew'),
(17, false, 'Henry', 'Taylor', '404 Cedar St', 'Cityville', 'Countryland', 'henry.taylor@example.com', 'crewPass7', 7777777777, 'crew'),
(18, false, 'Ivy', 'White', '505 Pine St', 'Townsville', 'Countryland', 'ivy.white@example.com', 'crewPass8', 8888888888, 'crew'),
(19, false, 'Jack', 'Davis', '606 Oak St', 'Villagetown', 'Countryland', 'jack.davis@example.com', 'crewPass9', 9999999999, 'crew'),
(20, false, 'Karen', 'Miller', '707 Birch St', 'Cityville', 'Countryland', 'karen.miller@example.com', 'crewPass10', 1010101010, 'crew');



-- Inserting data into ADDRESS table. Delete? 
INSERT INTO ADDRESS (street, city, country, userID)
VALUES
('123 Main St', 'New York', 'USA', 1),
('456 Main St', 'New York', 'USA', 2),
('789 Main St', 'New York', 'USA', 3);

-- Inserting aircrafts
INSERT INTO AIRCRAFT (aircraftID, aircraftModel, capacity)
VALUES
(5678, "A380", 200),
(8934, "B737", 180),
(1029, "B777", 220),
(5985, "A350", 250),
(8938, "B787", 190),
(2398, "B77L", 230);

-- Inserting CREW data, 6 crew members per flight
INSERT INTO CREW (crewID, pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4)
VALUES
('CR001', 11, 12, 13, 14, 15, 16),
('CR002', 17, 18, 19, 20, 13, 14),
('CR003', 11, 18, 15, 16, 19, 20),
('CR004', 12, 17, 14, 15, 16, 20),
('CR005', 11, 17, 16, 19, 20, 14),
('CR006', 12, 18, 14, 15, 16, 19);

-- Inserting DESTINATION data - possible destinations for airline
INSERT INTO DESTINATION (destinationID, destinationCountry, destinationCity)
VALUES
(7890, "Italy","Rome"),
(5678, "UK","London"),
(2674, "USA","New York"),
(0933, "France","Paris"),
(7899, 'USA', 'Los Angeles'),
(0239, 'Japan', 'Tokyo'),
(4378, 'Germany', 'Berlin'),
(2390, 'UAE', 'Dubai'),
(4588, 'Australia', 'Sydney');

-- Inserting flights
INSERT INTO FLIGHT (flightNumber, crewID, destination_Country, destination_city, origin_country, origin_city, capacity, departureDate, arrivalDate, aircraftID, aircraftModel)
VALUES
('FL001', 'CR001', 'UK', 'London', 'USA', 'New York', 200, '2023-12-01', '2023-12-02', 5678, "A380"),
('FL002', 'CR002', 'France', 'Paris', 'USA', 'Los Angeles', 180, '2023-12-05', '2023-12-06', 8934, "B737"),
('FL003', 'CR003', 'Japan', 'Tokyo', 'Germany', 'Berlin', 220, '2023-12-10', '2023-12-11', 1029, "B777");
-- ('FL004', 'CR004', 'UAE', 'Dubai', 'USA', 'New York', 250, '2023-12-15', '2023-12-16', 5985, "A350"),
-- ('FL005', 'CR005', 'Italy', 'Rome', 'UK', 'London', 190, '2023-12-20', '2023-12-21', 8938, "B787"),
-- ('FL006', 'CR006', 'Australia', 'Sydney', 'Japan', 'Tokyo', 230, '2023-12-25', '2023-12-26', 2398, "B77L");



-- Inserting SEAT data - 30 seats per flight, 10 first class, 20 economy, 10 seats per row
-- First Class
INSERT INTO SEAT (seatNumber, flightNumber, seatClass, isBooked)
VALUES
('1A', 'FL001', 'First Class', true),
('1B', 'FL001', 'First Class', true),
('1C', 'FL001', 'First Class', true),
('2A', 'FL001', 'First Class', true),
('2B', 'FL001', 'First Class', true),
('2C', 'FL001', 'First Class', true),
('3A', 'FL001', 'First Class', true),
('3B', 'FL001', 'First Class', true),
('3C', 'FL001', 'First Class', true),
('4A', 'FL001', 'First Class', false),
('4B', 'FL001', 'First Class', false),
('4C', 'FL001', 'First Class', false),
('5A', 'FL001', 'First Class', false),
('5B', 'FL001', 'First Class', false),
('5C', 'FL001', 'First Class', false),
('6A', 'FL001', 'Business Class', false),
('6B', 'FL001', 'Business Class', false),
('6C', 'FL001', 'Business Class', false),
('7A', 'FL001', 'Business Class', false),
('7B', 'FL001', 'Business Class', false),
('7C', 'FL001', 'Business Class', false),
('8A', 'FL001', 'Business Class', false),
('8B', 'FL001', 'Business Class', false),
('8C', 'FL001', 'Business Class', false),
('9A', 'FL001', 'Business Class', false),
('9B', 'FL001', 'Business Class', false),
('9C', 'FL001', 'Business Class', false),
('10A', 'FL001', 'Business Class', false),
('10B', 'FL001', 'Business Class', false),
('10C', 'FL001', 'Business Class', false),
('11A', 'FL001', 'Business Class', false),
('11B', 'FL001', 'Business Class', false),
('11C', 'FL001', 'Business Class', false),
('12A', 'FL001', 'Business Class', false),
('12B', 'FL001', 'Business Class', false),
('12C', 'FL001', 'Business Class', false),
('13A', 'FL001', 'Business Class', false),
('13B', 'FL001', 'Business Class', false),
('13C', 'FL001', 'Business Class', false),
('14A', 'FL001', 'Economy Class', false),
('14B', 'FL001', 'Economy Class', false),
('14C', 'FL001', 'Economy Class', false),
('15A', 'FL001', 'Economy Class', false),
('15B', 'FL001', 'Economy Class', false),
('15C', 'FL001', 'Economy Class', false),
('16A', 'FL001', 'Economy Class', false),
('16B', 'FL001', 'Economy Class', false),
('16C', 'FL001', 'Economy Class', false),
('17A', 'FL001', 'Economy Class', false),
('17B', 'FL001', 'Economy Class', false),
('17C', 'FL001', 'Economy Class', false),
('18A', 'FL001', 'Economy Class', false),
('18B', 'FL001', 'Economy Class', false),
('18C', 'FL001', 'Economy Class', false),
('19A', 'FL001', 'Economy Class', false),
('19B', 'FL001', 'Economy Class', false),
('19C', 'FL001', 'Economy Class', false),
('20A', 'FL001', 'Economy Class', false),
('20B', 'FL001', 'Economy Class', false),
('20C', 'FL001', 'Economy Class', false),
('1A', 'FL002', 'First Class', false),
('1B', 'FL002', 'First Class', false),
('1C', 'FL002', 'First Class', false),
('2A', 'FL002', 'First Class', false),
('2B', 'FL002', 'First Class', false),
('2C', 'FL002', 'First Class', false),
('3A', 'FL002', 'First Class', false),
('3B', 'FL002', 'First Class', false),
('3C', 'FL002', 'First Class', false),
('4A', 'FL002', 'First Class', false),
('4B', 'FL002', 'First Class', false),
('4C', 'FL002', 'First Class', false),
('5A', 'FL002', 'First Class', false),
('5B', 'FL002', 'First Class', false),
('5C', 'FL002', 'First Class', false),
('6A', 'FL002', 'Business Class', false),
('6B', 'FL002', 'Business Class', false),
('6C', 'FL002', 'Business Class', false),
('7A', 'FL002', 'Business Class', false),
('7B', 'FL002', 'Business Class', false),
('7C', 'FL002', 'Business Class', false),
('8A', 'FL002', 'Business Class', false),
('8B', 'FL002', 'Business Class', false),
('8C', 'FL002', 'Business Class', false),
('9A', 'FL002', 'Business Class', false),
('9B', 'FL002', 'Business Class', false),
('9C', 'FL002', 'Business Class', false),
('10A', 'FL002', 'Business Class', false),
('10B', 'FL002', 'Business Class', false),
('10C', 'FL002', 'Business Class', false),
('11A', 'FL002', 'Business Class', false),
('11B', 'FL002', 'Business Class', false),
('11C', 'FL002', 'Business Class', false),
('12A', 'FL002', 'Business Class', false),
('12B', 'FL002', 'Business Class', false),
('12C', 'FL002', 'Business Class', false),
('13A', 'FL002', 'Business Class', false),
('13B', 'FL002', 'Business Class', false),
('13C', 'FL002', 'Business Class', false),
('14A', 'FL002', 'Economy Class', false),
('14B', 'FL002', 'Economy Class', false),
('14C', 'FL002', 'Economy Class', false),
('15A', 'FL002', 'Economy Class', false),
('15B', 'FL002', 'Economy Class', false),
('15C', 'FL002', 'Economy Class', false),
('16A', 'FL002', 'Economy Class', false),
('16B', 'FL002', 'Economy Class', false),
('16C', 'FL002', 'Economy Class', false),
('17A', 'FL002', 'Economy Class', false),
('17B', 'FL002', 'Economy Class', false),
('17C', 'FL002', 'Economy Class', false),
('18A', 'FL002', 'Economy Class', false),
('18B', 'FL002', 'Economy Class', false),
('18C', 'FL002', 'Economy Class', false),
('19A', 'FL002', 'Economy Class', false),
('19B', 'FL002', 'Economy Class', false),
('19C', 'FL002', 'Economy Class', false),
('20A', 'FL002', 'Economy Class', false),
('20B', 'FL002', 'Economy Class', false),
('20C', 'FL002', 'Economy Class', false),
('1A', 'FL003', 'First Class', false),
('1B', 'FL003', 'First Class', false),
('1C', 'FL003', 'First Class', false),
('2A', 'FL003', 'First Class', false),
('2B', 'FL003', 'First Class', false),
('2C', 'FL003', 'First Class', false),
('3A', 'FL003', 'First Class', false),
('3B', 'FL003', 'First Class', false),
('3C', 'FL003', 'First Class', false),
('4A', 'FL003', 'First Class', false),
('4B', 'FL003', 'First Class', false),
('4C', 'FL003', 'First Class', false),
('5A', 'FL003', 'First Class', false),
('5B', 'FL003', 'First Class', false),
('5C', 'FL003', 'First Class', false),
('6A', 'FL003', 'Business Class', false),
('6B', 'FL003', 'Business Class', false),
('6C', 'FL003', 'Business Class', false),
('7A', 'FL003', 'Business Class', false),
('7B', 'FL003', 'Business Class', false),
('7C', 'FL003', 'Business Class', false),
('8A', 'FL003', 'Business Class', false),
('8B', 'FL003', 'Business Class', false),
('8C', 'FL003', 'Business Class', false),
('9A', 'FL003', 'Business Class', false),
('9B', 'FL003', 'Business Class', false),
('9C', 'FL003', 'Business Class', false),
('10A', 'FL003', 'Business Class', false),
('10B', 'FL003', 'Business Class', false),
('10C', 'FL003', 'Business Class', false),
('11A', 'FL003', 'Business Class', false),
('11B', 'FL003', 'Business Class', false),
('11C', 'FL003', 'Business Class', false),
('12A', 'FL003', 'Business Class', false),
('12B', 'FL003', 'Business Class', false),
('12C', 'FL003', 'Business Class', false),
('13A', 'FL003', 'Business Class', false),
('13B', 'FL003', 'Business Class', false),
('13C', 'FL003', 'Business Class', false),
('14A', 'FL003', 'Economy Class', false),
('14B', 'FL003', 'Economy Class', false),
('14C', 'FL003', 'Economy Class', false),
('15A', 'FL003', 'Economy Class', false),
('15B', 'FL003', 'Economy Class', false),
('15C', 'FL003', 'Economy Class', false),
('16A', 'FL003', 'Economy Class', false),
('16B', 'FL003', 'Economy Class', false),
('16C', 'FL003', 'Economy Class', false),
('17A', 'FL003', 'Economy Class', false),
('17B', 'FL003', 'Economy Class', false),
('17C', 'FL003', 'Economy Class', false),
('18A', 'FL003', 'Economy Class', false),
('18B', 'FL003', 'Economy Class', false),
('18C', 'FL003', 'Economy Class', false),
('19A', 'FL003', 'Economy Class', false),
('19B', 'FL003', 'Economy Class', false),
('19C', 'FL003', 'Economy Class', false),
('20A', 'FL003', 'Economy Class', false),
('20B', 'FL003', 'Economy Class', false),
('20C', 'FL003', 'Economy Class', false);




-- Inserting TICKET data
INSERT INTO TICKET(ticketNumber, flightNumber, passenger_fName, passenger_lName, seatNumber, seatClass, userID)
VALUES

('T001', 'FL001', 'John', 'Doe', '1A', 'First Class', 1),
('T002', 'FL001', 'Jane', 'Smith', '1B', 'First Class', 2),
('T003', 'FL001', 'Mike', 'Johnson', '1C', 'First Class', 3),
('T004', 'FL001', 'Emily', 'Williams', '6A', 'Business Class', 4),
('T005', 'FL001', 'Chris', 'Brown', '6B', 'Business Class', 5),
('T006', 'FL001', 'Sophia', 'Taylor', '6C', 'Business Class', 6),
('T007', 'FL001', 'Daniel', 'Miller', '16A', 'Economy Class', 7),
('T008', 'FL001', 'Olivia', 'Anderson', '16B', 'Economy Class', 8),
('T009', 'FL001', 'Aiden', 'White', '16C', 'Economy Class', 9);
-- ('T010', 'FL001', '', '', '7A', 'Economy Class', NULL),
-- ('T011', 'FL001', '', '', '7B', 'Economy Class', NULL),
-- ('T012', 'FL001', '', '', '7C', 'Economy Class', NULL),
-- ('T013', 'FL001', '', '', '18A', 'Business Class', NULL),
-- ('T014', 'FL001', '', '', '18B', 'Business Class', NULL),
-- ('T015', 'FL001', '', '', '18C', 'Business Class', NULL),
-- ('T016', 'FL001', '', '', '29A', 'Economy Class', NULL),
-- ('T017', 'FL001', '', '', '29B', 'Economy Class', NULL),
-- ('T018', 'FL001', '', '', '29C', 'Economy Class', NULL),
-- ('T019', 'FL001', '', '', '10A', 'Business Class', NULL),
-- ('T020', 'FL001', '', '', '10B', 'Business Class', NULL),
-- ('T021', 'FL001', '', '', '10C', 'Business Class', NULL),
-- ('T022', 'FL001', '', '', '20A', 'Business Class', NULL),
-- ('T023', 'FL001', '', '', '20B', 'Business Class', NULL),
-- ('T024', 'FL001', '', '', '20C', 'Business Class', NULL),
-- ('T025', 'FL001', '', '', '3A', 'First Class', NULL),
-- ('T026', 'FL001', '', '', '13B', 'Business Class', NULL),
-- ('T027', 'FL001', '', '', '23C', 'Economy Class', NULL),
-- ('T028', 'FL001', '', '', '14C', 'Business Class', NULL),
-- ('T029', 'FL001', '', '', '25A', 'Economy Class', NULL),
-- ('T030', 'FL001', '', '', '15C', 'Business Class', NULL);



-- Add more entries as needed
;


-- ALTER TABLE FLIGHT
-- ADD COLUMN crewID VARCHAR(7),
-- ADD FOREIGN KEY (crewID) REFERENCES CREW(crewID);

-- UPDATE FLIGHT SET crewID = 'C001' WHERE flightNumber = 'FL001';
-- -- Assign other crew members to corresponding flights...

-- -- displays properly rn
-- -- SELECT f.flightNumber, f.airline, c.pilot, c.copilot, c.flightAttendant1, c.flightAttendant2, c.flightAttendant3, c.flightAttendant4
-- -- FROM FLIGHT f
-- -- JOIN CREW c ON f.crewID = c.crewID;

-- SELECT * FROM TICKET;

-- -- SELECT * FROM CREW;

-- -- SELECT * FROM TICKET;