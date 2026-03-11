
INSERT INTO ClassStanding (ID, Name) VALUES
('FR', 'Freshman'),
('SO', 'Sophomore'),
('JR', 'Junior'),
('SR', 'Senior');


INSERT INTO TimeSlot (ID, StartTime, EndTime) VALUES
('A', '08:00:00', '09:15:00'),
('B', '09:30:00', '10:45:00'),
('C', '11:00:00', '12:15:00'),
('D', '12:30:00', '13:45:00'),
('E', '14:00:00', '15:15:00');


INSERT INTO State (ID, Name) VALUES
('AL', 'Alabama'),
('AK', 'Alaska'),
('AZ', 'Arizona'),
('CA', 'California'),
('CO', 'Colorado'),
('FL', 'Florida'),
('GA', 'Georgia'),
('NY', 'New York'),
('TX', 'Texas'),
('WA', 'Washington');


INSERT INTO CourseOffering (Name, Offered) 
VALUES
('Intro to CS'),
('Data Structures'),
('Algorithms'),
('Database Systems'),
('Linear Algebra'),
('Calculus I'),
('Physics I'),
('English Comp');


INSERT INTO Teacher (TeacherNum, FirstName, LastName, PhoneNum, Email, Street, Zipcode, StateID, isActive) VALUES
(1001, 'Alice',   'Johnson',  '15551234567', 'ajohnson@uni.edu',  '101 Maple St',    '90210', 'CA', true),
(1002, 'Brian',   'Smith',    '15552345678', 'bsmith@uni.edu',    '202 Oak Ave',     '30301', 'GA', true),
(1003, 'Carol',   'Williams', '15553456789', 'cwilliams@uni.edu', '303 Pine Rd',     '10001', 'NY', true),
(1004, 'David',   'Brown',    '15554567890', 'dbrown@uni.edu',    '404 Elm Blvd',    '73301', 'TX', false),
(1005, 'Eva',     'Davis',    '15555678901', 'edavis@uni.edu',    '505 Cedar Ln',    '98101', 'WA', true);


INSERT INTO Student (StudentNum, FirstName, LastName, PhoneNum, Email, Street, Zipcode, StateID, ClassStandingID, isActive) VALUES
(2001, 'Liam',    'Garcia',   '15556789012', 'lgarcia@uni.edu',   '11 Birch St',   '90210', 'CA', 'FR', true),
(2002, 'Olivia',  'Martinez', '15557890123', 'omartinez@uni.edu', '22 Walnut Ave', '30301', 'GA', 'SO', true),
(2003, 'Noah',    'Anderson', '15558901234', 'nanderson@uni.edu', '33 Spruce Rd',  '10001', 'NY', 'JR', true),
(2004, 'Emma',    'Taylor',   '15559012345', 'etaylor@uni.edu',   '44 Ash Blvd',   '73301', 'TX', 'SR', true),
(2005, 'James',   'Thomas',   '15550123456', 'jthomas@uni.edu',   '55 Poplar Ln',  '98101', 'WA', 'FR', false),
(2006, 'Sophia',  'Jackson',  '15551122334', 'sjackson@uni.edu',  '66 Willow Dr',  '85001', 'AZ', 'SO', true),
(2007, 'William', 'White',    '15552233445', 'wwhite@uni.edu',    '77 Magnolia Ct','80201', 'CO', 'JR', true),
(2008, 'Isabella','Harris',   '15553344556', 'iharris@uni.edu',   '88 Sycamore Pl','32801', 'FL', 'SR', true);


INSERT INTO Course (CourseNum, CourseOfferingID, TeacherID, Capacity, TimeSlotID) VALUES
(101, 1, 1, 30, 'A'),
(102, 2, 2, 25, 'B'),
(103, 3, 3, 20, 'C'),
(104, 4, 1, 28, 'D'),
(105, 5, 4, 35, 'E'),
(106, 6, 5, 30, 'A'),
(107, 7, 2, 24, 'C');


INSERT INTO GradeCategory (Name, Weight, CourseID) VALUES
('Homework',    30, 1),
('Midterm',     30, 1),
('Final',       40, 1),
('Homework',    25, 2),
('Quizzes',     25, 2),
('Midterm',     25, 2),
('Final',       25, 2),
('Homework',    20, 3),
('Midterm',     35, 3),
('Final',       45, 3),
('Homework',    30, 4),
('Midterm',     30, 4),
('Final',       40, 4);


INSERT INTO Enrollment (StudentID, CourseID) VALUES
(1, 1), (1, 2), (1, 4),
(2, 1), (2, 3),
(3, 2), (3, 4), (3, 5),
(4, 1), (4, 3), (4, 6),
(5, 2),
(6, 1), (6, 4), (6, 7),
(7, 3), (7, 5),
(8, 2), (8, 6), (8, 7);


INSERT INTO Assignment (Name, CategoryID, DueDate) VALUES
('HW 1',          1,  '2024-09-10 23:59:00'),
('HW 2',          1,  '2024-09-24 23:59:00'),
('HW 3',          1,  '2024-10-08 23:59:00'),
('Midterm Exam',  2,  '2024-10-15 10:00:00'),
('Final Exam',    3,  '2024-12-10 10:00:00'),
('HW 1',          4,  '2024-09-12 23:59:00'),
('HW 2',          4,  '2024-09-26 23:59:00'),
('Quiz 1',        5,  '2024-09-19 09:30:00'),
('Quiz 2',        5,  '2024-10-03 09:30:00'),
('Midterm Exam',  6,  '2024-10-17 09:30:00'),
('Final Exam',    7,  '2024-12-12 09:30:00'),
('HW 1',          8,  '2024-09-11 23:59:00'),
('HW 2',          8,  '2024-09-25 23:59:00'),
('Midterm Exam',  9,  '2024-10-16 11:00:00'),
('Final Exam',    10, '2024-12-11 11:00:00');

-- StudentGrade
INSERT INTO StudentGrade (AssignmentID, StudentID, Grade) VALUES
-- Student 1 in Course 1
(1, 1, 92.00), (2, 1, 88.50), (3, 1, 95.00),
(4, 1, 78.00), (5, 1, 85.00),
-- Student 2 in Course 1
(1, 2, 70.00), (2, 2, 75.00), (3, 2, 80.00),
(4, 2, 65.00), (5, 2, 72.00),
-- Student 4 in Course 1
(1, 4, 98.00), (2, 4, 96.00), (3, 4, 99.00),
(4, 4, 94.00), (5, 4, 97.00),
-- Student 6 in Course 1
(1, 6, 60.00), (2, 6, 55.00), (3, 6, NULL),
(4, 6, 58.00), (5, 6, NULL),
-- Student 3 in Course 2
(6, 3, 88.00), (7, 3, 91.00),
(8, 3, 85.00), (9, 3, 89.00),
(10, 3, 76.00), (11, 3, 80.00),
-- Student 5 in Course 2
(6, 5, 72.00), (7, 5, NULL),
(8, 5, 68.00), (9, 5, 74.00),
(10, 5, 70.00), (11, 5, NULL),
-- Student 3 in Course 3
(12, 3, 95.00), (13, 3, 93.00),
(14, 3, 89.00), (15, 3, 91.00),
-- Student 7 in Course 3
(12, 7, 77.00), (13, 7, 82.00),
(14, 7, 74.00), (15, 7, 79.00);