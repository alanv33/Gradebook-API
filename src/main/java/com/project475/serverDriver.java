/**
 * This class is the driver file that allows the user to pick options
 * Users can pick one of many numbers that corresponds to a client function
 ** Further input is handled by the client class 
 */
package com.project475;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class serverDriver {


    public static void main(String[] args) {
        int userInput = -1;
        client client = new client();
        Scanner sc = new Scanner(System.in); 
        
        System.out.println("Hi! Welcome to the gradebook system");

        // Loops until the user wants to exit the system
        while (userInput != 0) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("0. Exit");
            System.out.println("1. Add Assignments");
            System.out.println("2. Update Assignments");
            System.out.println("3. Delete Assignments");
            System.out.println("4. List All Courses Assignments");
            System.out.println("5. List All Courses for a Student");
            System.out.println("6. Create A Teacher");
            System.out.println("7. Update A Teacher");
            System.out.println("8. Delete A Teacher");
            System.out.println("9. Create A Course");
            System.out.println("10. Delete A Course");
            System.out.println("11. Create Grade Category");
            System.out.println("12. Update Grade Category");
            System.out.println("13. Delete Grade Category");
            System.out.println("14. Update an Assignment Grade for all");
            System.out.println("15. Create a Student");
            System.out.println("16. Update a Student");
            System.out.println("17. Enroll a Student in a Course");
            System.out.println("18. Drop a Student (deactivate)");
            System.out.println("19. List Students in Course");
            System.out.println("20. Get Student GPA");
            System.out.println("21. Search Students");
            System.out.println("22. List Teacher's Courses");
            System.out.println("23. List Grade Distribution");
            System.out.println("24. List Offered Courses");
            System.out.println("25. Calculate Course Grade");
            System.out.println("26. Update Single Assignment Grade");
            System.out.println("27. Create Course Offering");
            System.out.println("28. Update Course Offering");
            System.out.println("29. Delete Course Offering");
            System.out.println("30. List Student's Assignments in Course");
            System.out.println("Insert a number to select one of the options above:");

            userInput = sc.nextInt();
            sc.nextLine();

            switch (userInput) {
                case 0:
                    System.out.println("Goodbye!");
                    break;
                case 1:
                    client.addAssignments_Client(sc);
                    break;
                case 2:
                    client.updateAssigment_Client(sc);
                    break;
                case 3:
                    client.deleteAssignment_Client(sc);
                    break;
                case 4:
                    client.listAllCourseAssignments_Client(sc);
                    break;
                case 5:
                    client.listCoursesForStudent_Client(sc);
                    break;
                case 6:
                    client.createTeacher_Client(sc);
                    break;
                case 7:
                    client.updateTeacher_Client(sc);
                    break;
                case 8:
                    client.deleteTeacher_Client(sc);
                    break;
                case 9:
                    client.createCourse_Client(sc);
                    break;
                case 10:
                    client.deleteCourse_Client(sc);
                    break;
                case 11:
                    client.createGradeCategory(sc);
                    break;
                case 12:
                    client.updateGradeCategory(sc);
                    break;
                case 13:
                    client.deleteGradeCategory(sc);
                    break;
                case 14:
                    client.updateAssignmentGradeForAll(sc);
                    break;
                case 15:
                    client.createStudent_Client(sc);
                    break;
                case 16:
                    client.updateStudent_Client(sc);
                    break;
                case 17:
                    client.enrollStudent_Client(sc);
                    break;
                case 18:
                    client.dropStudent_Client(sc);
                    break;
                case 19:
                    client.listStudentsInCourse_Client(sc);
                    break;
                case 20:
                    client.getGPA_Client(sc);
                    break;
                case 21:
                    client.searchStudent_Client(sc);
                    break;
                case 22:
                    client.allTeacherCourses_Client(sc);
                    break;
                case 23:
                    client.listGradeDistribution_Client(sc);
                    break;
                case 24:
                    client.offeredCourses_Client(sc);
                    break;
                case 25:
                    client.calculateCourseGrade_Client(sc);
                    break;
                case 26:
                    client.updateAssignmentGrade_Client(sc);
                    break;
                case 27:
                    client.createCourseOffering_Client(sc);
                    break;
                case 28:
                    client.updateCourseOffering_Client(sc);
                    break;
                case 29:
                    client.deleteCourseOffering_Client(sc);
                    break;
                case 30:
                    client.listAssignmentsForStudent_Client(sc);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
        
        sc.close(); 
    }

    // Method used to intialize the database from the schema.sql file
    public static void initializeDatabase(Connection conn) {
        try {
            String sql = new String(Files.readAllBytes(Paths.get("schema.sql")));
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Database schema initialized successfully!");
        } catch (Exception e) {
            System.err.println("Failed to run schema.sql");
            e.printStackTrace();
        }
    }
}