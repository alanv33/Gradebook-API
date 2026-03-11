package com.project475;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import io.github.cdimascio.dotenv.Dotenv;

public class server {
    private Connection conn;

    public server() {
        /* -- DO NOT TOUCH -- */
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");

        // Attempt connection to DB
        try {
            this.conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }

    // Create a new student in the database. ID must be unique and is used to
    // identify the student for updates and deletes.
    // Remove all ID's, auto assigned by server
    public void createStudent(int studentNum, String firstName, String lastName,
            String email, String phoneNum, String street,
            String zipcode, String stateId, String classStandingId) {

        String sql = "INSERT INTO Student (StudentNum, FirstName, LastName, Email, PhoneNum, Street, Zipcode, StateID, ClassStandingID, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, true)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNum);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, phoneNum);
            pstmt.setString(6, street);
            pstmt.setString(7, zipcode);
            pstmt.setString(8, stateId);
            pstmt.setString(9, classStandingId);

            pstmt.executeUpdate();
            System.out.println("Student created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
    }

    // Update student information. ID is used to find the student and cannot be
    // changed.
    public void updateStudent(int studentNum, String firstName, String lastName,
            String email, String phoneNum, String street,
            String zipcode, String stateId, String classStandingId) {

        String sql = "UPDATE Student SET FirstName=?, LastName=?, Email=?, PhoneNum=?, Street=?, Zipcode=?, StateID=?, ClassStandingID=? WHERE StudentNum=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNum);
            pstmt.setString(5, street);
            pstmt.setString(6, zipcode);
            pstmt.setString(7, stateId);
            pstmt.setString(8, classStandingId);
            pstmt.setInt(9, studentNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("No student found with student number: " + studentNum);
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    // Enrolls a student in a course. Student and course must already exist.
    public void enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO Enrollment (StudentID, CourseID) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            pstmt.executeUpdate();
            System.out.println("Student enrolled successfully.");
        } catch (SQLException e) {
            System.out.println("Error enrolling student: " + e.getMessage());
        }
    }

    // Sets the student's isActive status to false. Student is not deleted from the
    // database and can be reactivated by setting isActive to true.
    public void deactivateStudent(int studentNum) {
        String sql = "UPDATE Student SET Active=false WHERE StudentNum=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student set to inactive.");
            } else {
                System.out.println("No student found with StudentNum: " + studentNum);
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void addAssignments(String name, String gradeCatName, String dueDate, int courseNum) {
        String findIdSql = "SELECT gc.ID FROM GradeCategory gc " +
                "JOIN Course c ON gc.CourseID = c.ID " +
                "WHERE gc.Name = ? AND c.CourseNum = ?";

        String insertSql = "INSERT INTO Assignment(Name, CategoryID, DueDate) VALUES(?,?,?::timestamp)";

        try {
            int categoryId = -1;
            try (PreparedStatement idStmt = conn.prepareStatement(findIdSql)) {
                idStmt.setString(1, gradeCatName);
                idStmt.setInt(2, courseNum);
                ResultSet rs = idStmt.executeQuery();

                if (rs.next()) {
                    categoryId = rs.getInt("ID");
                } else {
                    System.out.println("Error: Grade Category or Course not found.");
                    return;
                }
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, categoryId);
                pstmt.setString(3, dueDate);

                pstmt.executeUpdate();
                System.out.println("Assignment added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAssigment(String name, String column, ArrayList<String> newVal) {
        int gradeCatID = -1;
        String colName = "";
        Object valueToSet = null;
        if (column.equals("Grade Category")) {
            colName = "CategoryID";
            String gradeCatName = newVal.get(0);
            String courseNum = newVal.get(1);
            String findGCatID = "SELECT GradeCategory.ID FROM GradeCategory"
                    + " JOIN Course ON (Course.ID = GradeCategory.CourseID)"
                    + " WHERE Course.courseNum= ? AND GradeCategory.name = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(findGCatID)) {
                pstmt.setString(1, courseNum);
                pstmt.setString(2, gradeCatName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    gradeCatID = rs.getInt("ID");
                    valueToSet = gradeCatID;
                } else {
                    System.out.println("Error: Grade Category not found");
                    return;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            colName = column;
            valueToSet = newVal.get(0);

        }
        String sql = "UPDATE Assignment SET " + colName + " =? WHERE name=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, valueToSet);
            pstmt.setString(2, name);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Assignment Update Successful");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAssignment(String assignmentName, int courseNum) {
        String sql = "DELETE FROM Assignment WHERE name = ? AND categoryID IN" +
                " (SELECT gradeCategory.ID FROM gradeCategory" +
                " JOIN Course ON (Course.ID = gradeCategory.courseID)" +
                " WHERE course.coursenum = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, assignmentName);
            pstmt.setInt(2, courseNum);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listAllCourseAssignments(int courseNum) {
        String sql = "SELECT Assignment.name AS \"Assignment Name\", " +
                " gradeCategory.name AS \"Grade Category\", " +
                " gradeCategory.weight, " +
                " Assignment.dueDate " +
                " FROM Course " +
                " JOIN gradeCategory ON (gradeCategory.courseID = Course.ID) " +
                " JOIN Assignment ON (Assignment.categoryID = gradeCategory.ID) " +
                " WHERE Course.courseNum = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseNum);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Assignments for Course #" + courseNum + ":");
            System.out.println("------------------------------------------");

            while (rs.next()) {
                String name = rs.getString("Assignment Name");
                String cat = rs.getString("Grade Category");
                double weight = rs.getDouble("weight");
                String date = rs.getString("dueDate");

                System.out.println(name + " | Category: " + cat + " | Weight: " + weight + "% | Due: " + date);
            }
        } catch (SQLException e) {
            System.out.println("Query Error: " + e.getMessage());
        }
    }

    public void listCoursesForStudent(int studentNum) {
        String sql = "SELECT CourseOffering.name AS \"Course Name\"," +
                " Teacher.firstname AS \"First Name\"," +
                " Teacher.lastName AS \"Last Name\"," +
                " Course.Capacity," +
                " Course.timeSlotID" +
                " FROM Course " +
                " JOIN Enrollment ON (enrollment.courseID = Course.ID) " +
                " JOIN Student ON (Student.ID = enrollment.studentID) " +
                " JOIN Teacher ON (Teacher.ID = course.teacherID) " +
                " JOIN courseOffering ON (courseOffering.ID = course.courseOfferingID) " +
                " WHERE student.studentNum = ?" +
                " ORDER BY course.timeSlotID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNum);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Courses for Student #" + studentNum + ":");
            System.out.println("------------------------------------------");

            while (rs.next()) {
                String courseName = rs.getString("Course Name");
                String Teacher = rs.getString("First Name") + " " + rs.getString("Last Name");
                int capacity = rs.getInt("capacity");
                String timeSlot = rs.getString("timeSlotID");

                System.out.println("Course: " + courseName + " | " + "Teacher: " + Teacher + " | " + "Capacity: "
                        + capacity + " | " + "Time Slot: " + timeSlot);

            }
        } catch (SQLException e) {
            System.out.println("Query Error: " + e.getMessage());
        }
    }

    // Used to reactivate a student that was dropped. Sets the student's isActive
    // status to true.
    public void activateStudent(int studentNum) {
        String sql = "UPDATE Student SET Active=true WHERE StudentNum=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student set to active.");
            } else {
                System.out.println("No student found with StudentNum: " + studentNum);
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void createGradeCategory(int courseNumber, String gradeCategoryName, int gradeWeight) {
        String sql = "INSERT INTO GradeCategory (Name, Weight, CourseID) " +
                "VALUES (?, ?, (SELECT ID FROM Course WHERE CourseNum = ?))";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gradeCategoryName);
            pstmt.setInt(2, gradeWeight);
            pstmt.setInt(3, courseNumber);

            pstmt.executeUpdate();
            System.out.println("Grade Category created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Grade Category: " + e.getMessage());
        }
    }

    public void updateGradeCategory(int courseNumber, String gradeCategoryName, int newWeight) {
        String sql = "UPDATE GradeCategory SET Weight=? WHERE Name=? AND CourseID=(SELECT ID FROM Course WHERE CourseNum=?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newWeight);
            pstmt.setString(2, gradeCategoryName);
            pstmt.setInt(3, courseNumber);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade Category updated successfully.");
            } else {
                System.out.println("No category found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating grade: " + e.getMessage());
        }
    }

    public void deleteGradeCategory(int courseNumber, String gradeCategoryName) {
        String sql = "DELETE FROM GradeCategory WHERE CourseID = (SELECT ID FROM Course WHERE CourseNum = ?) AND Name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseNumber);
            pstmt.setString(2, gradeCategoryName);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade Category deleted successfully.");
            } else {
                System.out.println("No category found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting grade category: " + e.getMessage());
        }
    }

    public void dropStudent(int studentNum, int courseNum) {
        String sql = "DELETE FROM Enrollment WHERE StudentID = (SELECT ID FROM Student WHERE StudentNum = ?) " +
                "AND CourseID = (SELECT ID FROM Course WHERE CourseNum = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNum);
            pstmt.setInt(2, courseNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student dropped from course successfully.");
            } else {
                System.out.println("Enrollment not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error dropping student: " + e.getMessage());
        }
    }

    public void updateAssignmentGrade(String assignmentName, int studentNum, int courseNum, double grade) {
        String sql = "UPDATE StudentGrade SET Grade = ? WHERE AssignmentID = " +
                "(SELECT a.ID FROM Assignment a JOIN GradeCategory gc ON a.CategoryID = gc.ID " +
                "JOIN Course c ON gc.CourseID = c.ID WHERE a.Name = ? AND c.CourseNum = ?) " +
                "AND StudentID = (SELECT ID FROM Student WHERE StudentNum = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, grade);
            pstmt.setString(2, assignmentName);
            pstmt.setInt(3, courseNum);
            pstmt.setInt(4, studentNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Assignment grade updated successfully.");
            } else {
                System.out.println("No matching assignment grade found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating grade: " + e.getMessage());
        }
    }

    public Double calculateCourseGrade(int studentNum, int courseNum) {
        String sql = "SELECT SUM(sg.Grade * gc.Weight / 100.0) AS FinalGrade " +
                "FROM StudentGrade sg " +
                "JOIN Assignment a ON sg.AssignmentID = a.ID " +
                "JOIN GradeCategory gc ON a.CategoryID = gc.ID " +
                "JOIN Course c ON gc.CourseID = c.ID " +
                "WHERE sg.StudentID = (SELECT ID FROM Student WHERE StudentNum = ?) " +
                "AND c.CourseNum = ? " +
                "GROUP BY c.ID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNum);
            pstmt.setInt(2, courseNum);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double grade = rs.getDouble("FinalGrade");
                System.out.println("Current grade: " + grade);
                return grade;
            } else {
                System.out.println("No grades found for this student in this course.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error calculating grade: " + e.getMessage());
            return null;
        }
    }

    public void listStudentsInCourse(int courseNum) {
        String sql = "SELECT s.StudentNum, s.FirstName, s.LastName, s.Email " +
                "FROM Student s " +
                "JOIN Enrollment e ON s.ID = e.StudentID " +
                "JOIN Course c ON e.CourseID = c.ID " +
                "WHERE c.CourseNum = ? AND s.isActive = true";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseNum);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("Students in course " + courseNum + ":");
            while (rs.next()) {
                System.out.println(rs.getInt("StudentNum") + " - " +
                        rs.getString("FirstName") + " " +
                        rs.getString("LastName") + " (" +
                        rs.getString("Email") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error listing students: " + e.getMessage());
        }
    }

    public void createTeacher(int teacherNum, String firstName, String lastName, String phoneNum, String email,
            String street, String zipcode, String stateId, boolean isActive) {
        String sql = "INSERT INTO Teacher (TeacherNum, FirstName, LastName, PhoneNum, Email, Street, Zipcode, StateID, Active) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacherNum);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phoneNum);
            pstmt.setString(5, email);
            pstmt.setString(6, street);
            pstmt.setString(7, zipcode);
            pstmt.setString(8, stateId);
            pstmt.setBoolean(9, isActive);

            pstmt.executeUpdate();
            System.out.println("Teacher created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating teacher: " + e.getMessage());
        }
    }

    public void updateTeacher(int teacherNum, String firstName, String lastName, String phoneNum, String email,
            String street, String zipcode, String stateId, boolean isActive) {
        String sql = "UPDATE Teacher SET FirstName=?, LastName=?, PhoneNum=?, Email=?, Street=?, Zipcode=?, StateID=?, Active=? WHERE TeacherNum=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phoneNum);
            pstmt.setString(4, email);
            pstmt.setString(5, street);
            pstmt.setString(6, zipcode);
            pstmt.setString(7, stateId);
            pstmt.setBoolean(8, isActive);
            pstmt.setInt(9, teacherNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher updated successfully.");
            } else {
                System.out.println("No teacher found with TeacherNum: " + teacherNum);
            }
        } catch (SQLException e) {
            System.out.println("Error updating teacher: " + e.getMessage());
        }
    }

    public void deactivateTeacher(int teacherNum) {
        String sql = "UPDATE Teacher SET Active=false WHERE TeacherNum=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacherNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher set to inactive.");
            } else {
                System.out.println("No teacher found with TeacherNum: " + teacherNum);
            }
        } catch (SQLException e) {
            System.out.println("Error updating teacher: " + e.getMessage());
        }
    }

    public void activateTeacher(int teacherNum) {
        String sql = "UPDATE Teacher SET Active=true WHERE TeacherNum=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacherNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher set to active.");
            } else {
                System.out.println("No teacher found with TeacherNum: " + teacherNum);
            }
        } catch (SQLException e) {
            System.out.println("Error updating teacher: " + e.getMessage());
        }
    }

    // Ask Alan for help with this one, need to check if course offering exists
    // before creating course, also need to check if teacher exists and is active
    // before creating course
    public void createCourse(int courseNum, String courseName, int teacherNum, int capacity, String timeSlotID) {
        String findCourseOfferingNameSQL = "SELECT CourseOffering.name FROM CourseOffering" +
                "JOIN Course ON Course.CourseOfferingID = CourseOffering.ID";

        String insertSQL = "INSERT INTO Course(CourseNum, CourseName, TeacherNum, Capacity, TimeSlotID) VALUES (?, ?, ?, ?, ?)";

    try{
        int categoryId = -1;
        try(PreparedStatement idStmt = conn.prepareStatement(findCourseOfferingNameSQL)){
            idStmt.setString(1, courseName);
            idStmt.setInt(2, courseNum);
            ResultSet rs = idStmt.executeQuery();

            if(rs.next()){
                categoryId = rs.getInt("ID");
            } else {
                System.out.println("Error: Course Offering or Course not found.");
                return;
            }
        }
        try(PreparedStatement pstmt = conn.prepareStatement(insertSQL)){
            pstmt.setInt(1, teacherNum);
            pstmt.setInt(2, capacity);
            pstmt.setString(3, timeSlotID);

            pstmt.executeUpdate();
            System.out.println("Course created successfully!");
        }
    } catch(SQLException e){
        e.printStackTrace();
    }          
}

    public void deleteCourse(int courseNum) {
        String sql = "DELETE FROM Course WHERE CourseNum = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("No course found with CourseNum: " + courseNum);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
        }
    }
}
