package com.project475;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class server {
    private Connection conn;

    public server(Connection conn) {
        this.conn = conn;
    }

    // Create a new student in the database. ID must be unique and is used to
    // identify the student for updates and deletes.
    // Remove all ID's, auto assigned by server
    public void createStudent(int studentNum, String firstName, String lastName,
            String email, String phoneNum, String street,
            String zipcode, String stateId, String classStandingId) {

        String sql = "INSERT INTO Student (ID, StudentNum, FirstName, LastName, Email, PhoneNum, Street, Zipcode, StateID, ClassStandingID) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

        String sql = "UPDATE Student SET StudentNum=?, FirstName=?, LastName=?, Email=?, PhoneNum=?, Street=?, Zipcode=?, StateID=?, ClassStandingID=? WHERE ID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNum);
            pstmt.setString(5, street);
            pstmt.setString(6, zipcode);
            pstmt.setString(7, stateId);
            pstmt.setString(8, classStandingId);

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

        String insertSql = "INSERT INTO Assignment(Name, CategoryID, DueDate) VALUES(?,?,?)";

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

        String sql = "INSERT INTO Grade (courseNumber, gradeCategoryName, gradeWeight) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseNumber);
            pstmt.setString(2, gradeCategoryName);
            pstmt.setInt(3, gradeWeight);

            pstmt.executeUpdate();
            System.out.println("Grade Category created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Grade Category: " + e.getMessage());
        }
    }

    public void updateGradeCategory(int courseNumber, String gradeCategoryName, int gradeWeight) {
        String sql = "UPDATE GradeCategory SET gradeCategoryName=?, gradeWeight=?, WHERE courseNumber=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseNumber);
            pstmt.setString(2, gradeCategoryName);
            pstmt.setInt(3, gradeWeight);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade Category updated successfully.");
            } else {
                System.out.println("No category found with course number: " + courseNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error updating grade: " + e.getMessage());
        }

    }

}
