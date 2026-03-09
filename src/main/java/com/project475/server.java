package com.project475;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class server {
    private Connection conn;

    public server(Connection conn) {
        this.conn = conn;
    }
    // Create a new student in the database. ID must be unique and is used to identify the student for updates and deletes.
    // Remove all ID's, auto assigned by server
    public void createStudent(int studentNum, String firstName, String lastName, 
                              String email, String phoneNum, String street, 
                              String zipcode, String stateId, String classStandingId) {
        
        String sql = "INSERT INTO Student (ID, StudentNum, FirstName, LastName, Email, PhoneNum, Street, Zipcode, StateID, ClassStandingID) " +
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
// Update student information. ID is used to find the student and cannot be changed.
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
            pstmt.setInt(9, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("No student found with ID: " + id);
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

    // Currently deletes the student but not enrollments.
    // Could be changed to cascade deletes or soft detete by setting to inactive instead. (Database must be changed for this)
    public void dropStudent(int id) {
        String sql = "DELETE FROM Student WHERE ID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}