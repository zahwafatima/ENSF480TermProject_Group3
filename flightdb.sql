DROP DATABASE IF EXISTS FLIGHTDB;
CREATE DATABASE FLIGHTDB; 
USE FLIGHTDB;

-- airline in flight is not properly implemented yet***
-- will update data better later***
-- flight.crewList is being annoying***



CREATE TABLE AIRLINE (
    airlineID INT AUTO_INCREMENT,
    Airline_name VARCHAR(255) NOT NULL,
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
    PRIMARY KEY (userID)
);

-- Do we need this class or is it repetative since we already have these columns in the user table? Think we should delete.
CREATE TABLE ADDRESS (
    street VARCHAR(100),
    city VARCHAR(50),
    country VARCHAR(50),
    userID INT,
    PRIMARY KEY (street, city, country),
    FOREIGN KEY (userID) REFERENCES USERS(userID)
);

CREATE TABLE FLIGHT (
    flightNumber VARCHAR(5) NOT NULL,
    destination_Country VARCHAR(20) NOT NULL,
    destination_city VARCHAR(20) NOT NULL,
    origin_country VARCHAR(20) NOT NULL,
    origin_city VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    departureDate DATE,
    arrivalDate DATE,
    crewID VARCHAR(7),
    aircraftID INT, 
    aircraftModel VARCHAR(20) NOT NULL, 
    Airline_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (Airline_name) REFERENCES AIRLINE(Airline_name),
    PRIMARY KEY (flightNumber),
    FOREIGN KEY (crewID) REFERENCES CREW(crewID),
    FOREIGN KEY (aircraftID) REFERENCES AIRCRAFT(aircraftID),
    FOREIGN KEY (aircraaircraftModelftID) REFERENCES AIRCRAFT(model)
);

CREATE TABLE AIRCRAFT (
    aircraftID INT AUTO_INCREMENT,
    model VARCHAR(50) NOT NULL,
    Airline_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (Airline_name) REFERENCES AIRLINE(Airline_name),
    PRIMARY KEY (aircraftID)
);

CREATE TABLE CREW (
    crewID VARCHAR(7) NOT NULL,
    flightNumber VARCHAR(5), 
    pilot VARCHAR(50),
    copilot VARCHAR(50),
    flightAttendant1 VARCHAR(50),
    flightAttendant2 VARCHAR(50),
    flightAttendant3 VARCHAR(50),
    flightAttendant4 VARCHAR(50),
    PRIMARY KEY (crewID),
    FOREIGN KEY (flightNumber) REFERENCES FLIGHT(flightNumber) 
);


CREATE TABLE SEAT (
    seatNumber VARCHAR(3) NOT NULL,
    seatClass VARCHAR(50) NOT NULL,
    isBooked BOOLEAN,
    price DECIMAL(10,2),
    ticketNumber VARCHAR(5) NOT NULL,
    PRIMARY KEY (seatNumber, flightNumber),
    FOREIGN KEY (ticketNumber) REFERENCES TICKET(ticketNumber)
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
    FOREIGN KEY (seatNumber) REFERENCES SEAT(seatNumber),
    FOREIGN KEY (class) REFERENCES SEAT(seatClass)
);


-- Inserting data into USERS table
INSERT INTO USERS (isRegistered, firstName, lastName, street, city, country, email, pass, phoneNumber)
VALUES
(true, 'John', 'Doe', '123 Main St', 'Cityville', 'Countryland', 'john.doe@example.com', 'password123', 1234567890),
(true, 'Jane', 'Smith', '456 Oak St', 'Townsville', 'Countryland', 'jane.smith@example.com', 'pass456', 9876543210),
(true, 'Alice', 'Johnson', '789 Pine St', 'Villagetown', 'Countryland', 'alice.johnson@example.com', 'secretPass', 5551234567),
(false, 'Bob', 'Brown', '101 Maple St', 'Cityville', 'Countryland', 'bob.brown@example.com', 'password987', 3216549870),
(false, 'Eva', 'Miller', '202 Elm St', 'Townsville', 'Countryland', 'eva.miller@example.com', 'pass789', 7894561230),
(true, 'Michael', 'Davis', '303 Birch St', 'Villagetown', 'Countryland', 'michael.davis@example.com', 'myPassword', 6549873210),
(false, 'Sophia', 'Anderson', '404 Cedar St', 'Cityville', 'Countryland', 'sophia.anderson@example.com', 'securePass', 4567890123),
(false, 'Daniel', 'White', '505 Pine St', 'Townsville', 'Countryland', 'daniel.white@example.com', 'danielPass', 7890123456),
(true, 'Olivia', 'Moore', '606 Oak St', 'Villagetown', 'Countryland', 'olivia.moore@example.com', 'oliviaPass', 1597534680),
(false, 'James', 'Taylor', '707 Birch St', 'Cityville', 'Countryland', 'james.taylor@example.com', 'james123', 3571592468);


-- Inserting data into ADDRESS table. Delete? 
INSERT INTO ADDRESS (street, city, country, userID)
VALUES
('123 Main St', 'New York', 'USA', 1),
('456 Main St', 'New York', 'USA', 2),
('789 Main St', 'New York', 'USA', 3);

-- Inserting flights
INSERT INTO FLIGHT (flightNumber, airline, destination_Country, destination_city, origin_country, origin_city, capacity, departureDate, arrivalDate)
VALUES
('FL001', 'Delta', 'UK', 'London', 'USA', 'New York', 200, '2023-12-01', '2023-12-02'),
('FL002', 'United', 'France', 'Paris', 'USA', 'Los Angeles', 180, '2023-12-05', '2023-12-06'),
('FL003', 'Lufthansa', 'Japan', 'Tokyo', 'Germany', 'Berlin', 220, '2023-12-10', '2023-12-11'),
('FL004', 'Emirates', 'UAE', 'Dubai', 'USA', 'New York', 250, '2023-12-15', '2023-12-16'),
('FL005', 'British Airways', 'Italy', 'Rome', 'UK', 'London', 190, '2023-12-20', '2023-12-21'),
('FL006', 'Qatar Airways', 'Australia', 'Sydney', 'Japan', 'Tokyo', 230, '2023-12-25', '2023-12-26');



-- Inserting CREW data, 6 crew members per flight
INSERT INTO CREW (crewID, flightNumber, pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4)
VALUES
('CR001', 'FL001', 'John Smith', 'Alice Johnson', 'Emily Davis', 'Michael Brown', 'Jessica Wilson', 'David Miller'),
('CR002', 'FL002', 'Robert Anderson', 'Olivia Martinez', 'Daniel Taylor', 'Sophia Jackson', 'Ethan Moore', 'Ava White'),
('CR003', 'FL003', 'William Davis', 'Emma Harris', 'Matthew Jones', 'Lily Anderson', 'Christopher Wilson', 'Grace Martin'),
('CR004', 'FL004', 'James Taylor', 'Sophia Wilson', 'Andrew Moore', 'Olivia Harris', 'Samuel Jackson', 'Ava Davis'),
('CR005', 'FL005', 'Ethan Brown', 'Ava Johnson', 'Michael Smith', 'Emily Martin', 'Daniel White', 'Sophia Taylor'),
('CR006', 'FL006', 'Olivia White', 'Daniel Moore', 'Grace Anderson', 'David Taylor', 'Sophia Smith', 'Ethan Jackson');



-- Inserting SEAT data - 30 seats per flight, 10 first class, 20 economy, 10 seats per row
-- First Class
INSERT INTO SEAT (seatNumber, seatClass, isBooked, price, ticketNumber)
VALUES
('1A', 'First Class', false, 1500.00, 'T001'),
('1B', 'First Class', false, 1500.00, 'T002'),
('1C', 'First Class', false, 1500.00, 'T003'),
('2A', 'First Class', false, 1500.00, 'T004'),
('2B', 'First Class', false, 1500.00, 'T005'),
('2C', 'First Class', false, 1500.00, 'T006'),
('3A', 'First Class', false, 1500.00, 'T007'),
('3B', 'First Class', false, 1500.00, 'T008'),
('3C', 'First Class', false, 1500.00, 'T009'),
('4A', 'First Class', false, 1500.00, 'T010'),
('4B', 'First Class', false, 1500.00, 'T011'),
('4C', 'First Class', false, 1500.00, 'T012'),
('5A', 'First Class', false, 1500.00, 'T013'),
('5B', 'First Class', false, 1500.00, 'T014'),
('5C', 'First Class', false, 1500.00, 'T015'),
('6A', 'Business Class', false, 1000.00, 'T016'),
('6B', 'Business Class', false, 1000.00, 'T017'),
('6C', 'Business Class', false, 1000.00, 'T018'),
('7A', 'Business Class', false, 1000.00, 'T019'),
('7B', 'Business Class', false, 1000.00, 'T020'),
('7C', 'Business Class', false, 1000.00, 'T021'),
('8A', 'Business Class', false, 1000.00, 'T022'),
('8B', 'Business Class', false, 1000.00, 'T023'),
('8C', 'Business Class', false, 1000.00, 'T024'),
('9A', 'Business Class', false, 1000.00, 'T025'),
('9B', 'Business Class', false, 1000.00, 'T026'),
('9C', 'Business Class', false, 1000.00, 'T027'),
('10A', 'Business Class', false, 1000.00, 'T028'),
('10B', 'Business Class', false, 1000.00, 'T029'),
('10C', 'Business Class', false, 1000.00, 'T030'),
('11A', 'Business Class', false, 1000.00, 'T031'),
('11B', 'Business Class', false, 1000.00, 'T032'),
('11C', 'Business Class', false, 1000.00, 'T033'),
('12A', 'Business Class', false, 1000.00, 'T034'),
('12B', 'Business Class', false, 1000.00, 'T035'),
('12C', 'Business Class', false, 1000.00, 'T036'),
('13A', 'Business Class', false, 1000.00, 'T037'),
('13B', 'Business Class', false, 1000.00, 'T038'),
('13C', 'Business Class', false, 1000.00, 'T039'),
('14A', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 1000.00, 'T040'),
('14B', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 1000.00, 'T041'),
('14C', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 1000.00, 'T042'),
('15A', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 1000.00, 'T043'),
('15B', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 1000.00, 'T044'),
('15C', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 1000.00, 'T045'),
('16A', 'Economy Class', false, 500.00, 'T046'),
('16B', 'Economy Class', false, 500.00, 'T047'),
('16C', 'Economy Class', false, 500.00, 'T048'),
('17A', 'Economy Class', false, 500.00, 'T049'),
('17B', 'Economy Class', false, 500.00, 'T050'),
('17C', 'Economy Class', false, 500.00, 'T051'),
('18A', 'Economy Class', false, 500.00, 'T052'),
('18B', 'Economy Class', false, 500.00, 'T053'),
('18C', 'Economy Class', false, 500.00, 'T054'),
('19A', 'Economy Class', false, 500.00, 'T055'),
('19B', 'Economy Class', false, 500.00, 'T056'),
('19C', 'Economy Class', false, 500.00, 'T057'),
('20A', 'Economy Class', false, 500.00, 'T058'),
('20B', 'Economy Class', false, 500.00, 'T059'),
('20C', 'Economy Class', false, 500.00, 'T060');



-- Inserting TICKET data
INSERT INTO TICKET(ticketNumber, flightNumber, passenger_fName, passenger_lName, seatNumber, class, userID)
VALUES

('T001', 'FL001', 'John', 'Doe', '1A', 'First Class', 1),
('T002', 'FL001', 'Jane', 'Smith', '1B', 'First Class', 2),
('T003', 'FL001', 'Mike', 'Johnson', '1C', 'First Class', 3),

('T004', 'FL001', 'Emily', 'Williams', '6A', 'Business Class', 4),
('T005', 'FL001', 'Chris', 'Brown', '6B', 'Business Class', 5),
('T006', 'FL001', 'Sophia', 'Taylor', '6C', 'Business Class', 6),

('T007', 'FL001', 'Daniel', 'Miller', '16A', 'Economy Class', 7),
('T008', 'FL001', 'Olivia', 'Anderson', '16B', 'Economy Class', 8),
('T009', 'FL001', 'Aiden', 'White', '16C', 'Economy Class', 9),
('T010', 'FL001', '', '', '7A', 'Economy Class', NULL),
('T011', 'FL001', '', '', '7B', 'Economy Class', NULL),
('T012', 'FL001', '', '', '7C', 'Economy Class', NULL),
('T013', 'FL001', '', '', '18A', 'Business Class', NULL),
('T014', 'FL001', '', '', '18B', 'Business Class', NULL),
('T015', 'FL001', '', '', '18C', 'Business Class', NULL),
('T016', 'FL001', '', '', '29A', 'Economy Class', NULL),
('T017', 'FL001', '', '', '29B', 'Economy Class', NULL),
('T018', 'FL001', '', '', '29C', 'Economy Class', NULL),
('T019', 'FL001', '', '', '10A', 'Business Class', NULL),
('T020', 'FL001', '', '', '10B', 'Business Class', NULL),
('T021', 'FL001', '', '', '10C', 'Business Class', NULL),
('T022', 'FL001', '', '', '20A', 'Business Class', NULL),
('T023', 'FL001', '', '', '20B', 'Business Class', NULL),
('T024', 'FL001', '', '', '20C', 'Business Class', NULL),
('T025', 'FL001', '', '', '3A', 'First Class', NULL),
('T026', 'FL001', '', '', '13B', 'Business Class', NULL),
('T027', 'FL001', '', '', '23C', 'Economy Class', NULL),
('T028', 'FL001', '', '', '14C', 'Business Class', NULL),
('T029', 'FL001', '', '', '25A', 'Economy Class', NULL),
('T030', 'FL001', '', '', '15C', 'Business Class', NULL);



-- Add more entries as needed
;


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