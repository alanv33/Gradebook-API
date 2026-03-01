package com.project475;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
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