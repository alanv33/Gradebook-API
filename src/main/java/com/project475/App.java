package com.project475;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        /* -- DO NOT TOUCH --  */
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");

        // Attempt connection to DB
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("✅ Connected to Supabase!");

            // Connection object (Kinda like a scanner)
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
}