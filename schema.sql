/*
-- Uncomment this section of the code when testing on your machine --
-- Keep this sectiom commented out if running on the server -- 
\c postgres

CREATE DATABASE dbAnything;
\c dbAnything

SELECT current_database();
*/

CREATE TABLE IF NOT EXISTS ClassStanding(
    ID          VARCHAR(2) NOT NULL,
    Name        VARCHAR(20) NOT NULL,

    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS TimeSlot(
    ID          VARCHAR(1) NOT NULL,
    StartTime   TIME NOT NULL,
    EndTime     TIME NOT NULL,

    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS State(
    ID          VARCHAR(2),
    Name        VARCHAR(20),

    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS CourseOffering(
    ID          INTEGER NOT NULL,
    Name        VARCHAR(20) NOT NULL UNIQUE,
    Offered     BOOLEAN NOT NULL,

    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Student(
    ID          INTEGER NOT NULL,
    StudentNum  INTEGER NOT NULL UNIQUE,
    FirstName   VARCHAR(40) NOT NULL,
    LastName    VARCHAR(40) NOT NULL,
    PhoneNum    VARCHAR(11) NOT NULL,
    Email       VARCHAR(20) NOT NULL,
    Street      VARCHAR(40) NOT NULL,
    Zipcode     VARCHAR(5) NOT NULL,
    StateID     VARCHAR(2) NOT NULL,
    ClassStandingID VARCHAR(2) NOT NULL,

    PRIMARY KEY (ID),
    Foreign Key (StateID) references State(ID) DEFERRABLE INITIALLY DEFERRED,
    Foreign Key (ClassStandingID) references ClassStanding(ID) DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE IF NOT EXISTS Teacher(
    ID          INTEGER NOT NULL,
    TeacherNum  INTEGER NOT NULL UNIQUE,
    FirstName   VARCHAR(40) NOT NULL,
    LastName    VARCHAR(40) NOT NULL,
    PhoneNum    VARCHAR(11) NOT NULL UNIQUE,
    Email       VARCHAR(20) NOT NULL UNIQUE,
    Street      VARCHAR(40) NOT NULL,
    Zipcode     VARCHAR(5) NOT NULL,
    StateID     VARCHAR(2) NOT NULL,

    PRIMARY KEY (ID),
    Foreign Key (StateID) references State(ID) DEFERRABLE INITIALLY DEFERRED
);


CREATE TABLE IF NOT EXISTS Course(
    ID          INTEGER NOT NULL,
    CourseNum   INTEGER NOT NULL,
    CourseOfferingID    INTEGER NOT NULL,
    TeacherID   INTEGER NOT NULL,
    Capacity    INTEGER NOT NULL,
    TimeSlotID  VARCHAR(1) NOT NULL,

    PRIMARY KEY (ID),
    Foreign Key (CourseOfferingID) references CourseOffering(ID) DEFERRABLE INITIALLY DEFERRED,
    Foreign Key (TeacherID) references Teacher(ID) DEFERRABLE INITIALLY DEFERRED,
    Foreign Key (TimeSlotID) references TimeSlot(ID) DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE IF NOT EXISTS GradeCategory(
    ID          INTEGER NOT NULL,
    Name        VARCHAR(20) NOT NULL,
    Weight      DECIMAL(2,0) NOT NULL,
    CourseID    INTEGER NOT NULL,

    PRIMARY KEY (ID),
    Foreign Key (CourseID) references Course(ID) DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE IF NOT EXISTS Enrollment(
    StudentID   INTEGER NOT NULL,
    CourseID    INTEGER NOT NULL,

    PRIMARY KEY (StudentID, CourseID),
    Foreign Key (StudentID) references Student(ID) DEFERRABLE INITIALLY DEFERRED,
    Foreign Key (CourseID) references Course(ID) DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE IF NOT EXISTS Assignment(
    ID          INTEGER NOT NULL,
    Name        VARCHAR(60) NOT NULL,
    CategoryID  INTEGER NOT NULL,
    DueDate     TIMESTAMP NOT NULL,

    PRIMARY KEY (ID),
    Foreign Key (CategoryID) references GradeCategory(ID) DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE IF NOT EXISTS StudentGrade(
    ID          INTEGER NOT NULL,
    AssignmentID    INTEGER NOT NULL,
    StudentID       INTEGER NOT NULL,
    Grade           DECIMAL(4,2),

    PRIMARY KEY (ID),
    Foreign Key (AssignmentID) references Assignment(ID) DEFERRABLE INITIALLY DEFERRED,
    Foreign Key (StudentID) references Student(ID) DEFERRABLE INITIALLY DEFERRED
);


