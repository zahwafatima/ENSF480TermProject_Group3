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
    isRegistered VARCHAR(50),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    street VARCHAR(100),
    city VARCHAR(50),
    country VARCHAR(50),
    email VARCHAR(100),
    pass VARCHAR(100),
    phoneNumber VARCHAR(100),
    roles VARCHAR(100),
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

CREATE TABLE AIRCRAFT (
    aircraftID INT,
    aircraftModel VARCHAR(50) NOT NULL,
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
    departureDate DATE,
    arrivalDate DATE,
    aircraftID INT, 
    aircraftModel VARCHAR(20) NOT NULL, 
    
    PRIMARY KEY (flightNumber),
    FOREIGN KEY (crewID) REFERENCES CREW(crewID),
    FOREIGN KEY (aircraftID) REFERENCES AIRCRAFT(aircraftID),
    FOREIGN KEY (aircraftModel) REFERENCES AIRCRAFT(model)
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

-- Inserting flights
INSERT INTO FLIGHT (flightNumber, crewID, destination_Country, destination_city, origin_country, origin_city, capacity, departureDate, arrivalDate, aircraftID, aircraftModel)
VALUES
('FL001', 'CR001', 'UK', 'London', 'USA', 'New York', 200, '2023-12-01', '2023-12-02', 5678, "A380"),
('FL002', 'CR002', 'France', 'Paris', 'USA', 'Los Angeles', 180, '2023-12-05', '2023-12-06', 8934, "B737"),
('FL003', 'CR003', 'Japan', 'Tokyo', 'Germany', 'Berlin', 220, '2023-12-10', '2023-12-11', 1029, "B777"),
('FL004', 'CR004', 'UAE', 'Dubai', 'USA', 'New York', 250, '2023-12-15', '2023-12-16', 5985, "A350"),
('FL005', 'CR005', 'Italy', 'Rome', 'UK', 'London', 190, '2023-12-20', '2023-12-21', 8938, "B787"),
('FL006', 'CR006', 'Australia', 'Sydney', 'Japan', 'Tokyo', 230, '2023-12-25', '2023-12-26', 2398, "B77L");



-- Inserting CREW data, 6 crew members per flight
INSERT INTO CREW (crewID, pilot, copilot, flightAttendant1, flightAttendant2, flightAttendant3, flightAttendant4)
VALUES
('CR001', 11, 12, 13, 14, 15, 16),
('CR002', 17, 18, 19, 20, 13, 14),
('CR003', 11, 18, 15, 16, 19, 20),
('CR004', 12, 17, 14, 15, 16, 20),
('CR005', 11, 17, 16, 19, 20, 14),
('CR006', 12, 18, 14, 15, 16, 19);



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