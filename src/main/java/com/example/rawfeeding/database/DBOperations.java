package com.example.rawfeeding.database;

import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Meal;
import com.example.rawfeeding.meal.Measurement;

import java.sql.*;
import java.util.ArrayList;

public class DBOperations {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static CallableStatement callStatement = null;
    private static ResultSet rs = null;



    public static void executeDatabaseStatement(String successMessage, String failureMessage) {
        // sets the static connection to the database
        if (conn != null) {
            try  {
                // Execute the query
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(successMessage);
                } else {
                    System.out.println(failureMessage);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }





    public static void insertCategory(String name, String description, String image_url) {
        conn = DatabaseManager.getConnection();
        // sets the static connection to the database
        String insertQuery = "INSERT INTO food_categories (name, description, image_url) VALUES (?, ?, ?)";
        try
        {
            pstmt = conn.prepareStatement(insertQuery);
            // Set the values for the statement
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, image_url);
            // performs the actual insert
            // Used only to reduce repetitive code
            executeDatabaseStatement("Food category data inserted successfully", "Failed to insert food category data");
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
    }
}
