package com.project475;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;

public class serverDriver {


    public static void main(String[] args) {
<<<<<<< HEAD
        /* -- DO NOT TOUCH --  */
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");

        // Attempt connection to DB
        try (Connection conn = DriverManager.getConnection(url)) {    // Connection object (Kinda like a scanner) 
            System.out.println("Connected to Supabase!");


            // This can only be used once. Each Statement is one and done
            // But also not really. If you wanna know more lmk
            Statement stmt = conn.createStatement();
            
            // Test command
            ResultSet rs = stmt.executeQuery("SELECT NOW();");

            if (rs.next()) {
                System.out.println("Database Time: " + rs.getTimestamp(1));
                System.out.println("Test Successful!");
            }

        } catch (Exception e) {
            System.err.println("Connection failed!");
            // This will tell you if the password was wrong or the URL was invalid
            e.printStackTrace();
        }
=======
        System.out.println("Hi! Welcome to the gradebook system. \nWhat would you like to do?");
        System.out.println("1. Add Assignments");
        System.out.println("2. Update Assignments");
        System.out.println("3. Delete Assignments");
        System.out.println("4. List All Courses Assignments");

        Scanner sc = new Scanner(System.in);
        int userInput = sc.nextInt();
        client client = new client();

        switch(userInput){
            case 1:
                client.addAssignments_Client();
                break;
            case 2:
                client.updateAssigment_Client();
                break;
            case 3:
                client.deleteAssignment();
                break;
            case 4:
                client.listAllCourseAssignments();
                break;
            default:
                System.out.println("Invalid input");
        }

        sc.close();
>>>>>>> f2c9edc (Added driver file and some client functionality)
    }


    /* Intializes the database according to the schema.sql file
        Do not use this function unless we change the schema*/
    public static void initializeDatabase(Connection conn) {
    try {
        // Turns the file into a string
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