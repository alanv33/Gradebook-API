package com.project475;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class serverDriver {
    public static void main(String[] args) {
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