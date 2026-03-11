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