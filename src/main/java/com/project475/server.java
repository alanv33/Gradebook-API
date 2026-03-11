package com.project475;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class server {
    private Connection conn;

    public server(Connection conn) {
        this.conn = conn;
    }

    // Create a new student in the database. ID must be unique and is used to
    // identify the student for updates and deletes.
    // Remove all ID's, auto assigned by server
    public String createStudent(int studentNum, String firstName, String lastName,
            String email, String phoneNum, String street,
            String zipcode, String stateId, String classStandingId) {

        String sql = "INSERT INTO Student (StudentNum, FirstName, LastName, Email, PhoneNum, Street, Zipcode, StateID, ClassStandingID) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            return "Student created successfully.";
        } catch (SQLException e) {
            System.out.println("Error creating student: " + e.getMessage());
            return "Error creating student: " + e.getMessage();
        }
    }

    // Update student information. Calls the performUpdate helper function to update specified fields.
    // Replaced deactivate and activate student
    public String updateStudent(int studentNum, String firstName, String lastName,
            String email, String phoneNum, String street,
            String zipcode, String stateId, String classStandingId) {

        Map<String, Object> data = new HashMap<>(); 
                data.put("FirstName", firstName);
                data.put("LastName", lastName);
                data.put("Email", email);
                data.put("PhoneNum", phoneNum);
                data.put("Street", street);
                data.put("Zipcode", zipcode);
                data.put("StateID", stateId);
                data.put("ClassStandingID", classStandingId);
        return performUpdate("Student", "StudentNum", studentNum, data);
        }

    // Enrolls a student in a course. Student and course must already exist.
    public String enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO Enrollment (StudentID, CourseID) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            pstmt.executeUpdate();
            System.out.println("Student enrolled successfully.");
            return "Student enrolled successfully.";
        } catch (SQLException e) {
            System.out.println("Error enrolling student: " + e.getMessage());
            return "Error enrolling student: " + e.getMessage();
        }
    }

    // Takes a list of students to create or update. If a student with the given StudentNum already exists, that student's information will be updated.
    // If not, a new student will be created.
    public String crupdateStudents(List<Map<String, Object>> students) {
    String sql = "INSERT INTO Student (StudentNum, FirstName, LastName, Email, PhoneNum, Street, Zipcode, StateID, ClassStandingID, isActive) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                 "ON CONFLICT (StudentNum) DO UPDATE SET " +
                 "FirstName = EXCLUDED.FirstName, " +
                 "LastName = EXCLUDED.LastName, " +
                 "Email = EXCLUDED.Email, " +
                 "PhoneNum = EXCLUDED.PhoneNum, " +
                 "isActive = EXCLUDED.isActive;";

    try {

        conn.setAutoCommit(false); // START TRANSACTION
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map<String, Object> s : students) {
                pstmt.setInt(1, (int) s.get("StudentNum"));
                pstmt.setString(2, (String) s.get("FirstName"));
                pstmt.setString(3, (String) s.get("LastName"));
                pstmt.setString(4, (String) s.get("Email"));
                pstmt.setString(5, (String) s.get("PhoneNum"));
                pstmt.setString(6, (String) s.get("Street"));
                pstmt.setString(7, (String) s.get("Zipcode"));
                pstmt.setString(8, (String) s.get("StateID"));
                pstmt.setString(9, (String) s.get("ClassStandingID"));
                pstmt.setBoolean(10, (boolean) s.getOrDefault("isActive", true));
                
                pstmt.addBatch(); // Add to the local batch
            }
            pstmt.executeBatch(); // Send the entire batch to the DB
        }

        conn.commit(); // FINISH TRANSACTION
        return "SUCCESS: Processed " + students.size() + " students.";

    } catch (SQLException e) {
        try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        return "FAILURE: Aborted and rolled back. Error: " + e.getMessage();
    } finally {
        try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
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

                if(rs.next()){
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
            if(rows > 0){
                System.out.println("Assignment Update Successful");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public String dropStudent(int studentNum, int courseNum) {
        String sql = "DELETE FROM Enrollment WHERE StudentID = (SELECT ID FROM Student WHERE StudentNum = ?) " +
                "AND CourseID = (SELECT ID FROM Course WHERE CourseNum = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentNum);
            pstmt.setInt(2, courseNum);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student dropped from course successfully.");
                return "Student dropped from course successfully.";
            } else {
                System.out.println("Enrollment not found.");
                return "Enrollment not found.";
            }
        } catch (SQLException e) {
            System.out.println("Error dropping student: " + e.getMessage());
            return "Error dropping student: " + e.getMessage();
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

    // Helper to perform updates on Student table. 
    // Takes the table name, the primary key column name, the id of the row to update, and a map of column names to new values.
    // Only updates columns that are included in the map. Columns not included will remain unchanged.
    private String performUpdate(String table, String pkColumn, Object id, Map<String, Object> fields) {
    if (fields == null || fields.isEmpty()) {
        return "No fields to update.";
    }

    // Capture keys in a list to guarantee identical order for both loops
    List<String> columnNames = new ArrayList<>(fields.keySet());
    List<String> setClauses = new ArrayList<>();

    for (String col : columnNames) {
        // COALESCE only allows updating the fields included in the map. If a field is not included, it will be set to its current value (i.e. remain unchanged)
        setClauses.add(String.format("\"%s\" = COALESCE(?, \"%s\")", col, col));
    }     

    String sql = String.format("UPDATE \"%s\" SET %s WHERE \"%s\" = ?",
                                     table, String.join(", ", setClauses), pkColumn);

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        int i = 1;
        
        // Use the exact same list to pull the VALUES
        for (String col : columnNames) {
            // Get the actual data (the Object) using the key
            pstmt.setObject(i++, fields.get(col)); 
        }
        
        pstmt.setObject(i, id); // The final ? for the WHERE clause

        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0 ? "Update successful." : "Record not found.";
    } catch (SQLException e) {
        return "Error performing update: " + e.getMessage();
    }
}
}