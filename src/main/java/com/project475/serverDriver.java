package com.project475;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;

public class serverDriver {


    public static void main(String[] args) {
        int userInput = -1;
        client client = new client();
        Scanner sc = new Scanner(System.in); 
        
        System.out.println("Hi! Welcome to the gradebook system");

        while (userInput != 0) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("0. Exit");
            System.out.println("1. Add Assignments");
            System.out.println("2. Update Assignments");
            System.out.println("3. Delete Assignments");
            System.out.println("4. List All Courses Assignments");
            System.out.println("5. List All Courses for a Student");
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
                default:
                    System.out.println("Invalid input");
            }
        }
        
        sc.close(); 
    }

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