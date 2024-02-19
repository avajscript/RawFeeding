package com.example.rawfeeding.database;

import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Measurement;

import java.sql.*;
import java.util.ArrayList;

public class DBOperations {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static CallableStatement callStatement = null;
    private static ResultSet rs = null;

    /**
     * Used to insert a user into the database
     * @param username
     * @param email
     * @param password
     */
    public static void insertUser(String username, String email, String password) {
        // sets the static connection to the database
        String insertQuery = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, MD5(?))";
        try {

               conn = DatabaseManager.getConnection();
               pstmt = conn.prepareStatement(insertQuery);


            // Set the values for the statement
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            // performs the actual insert
            // Used only to reduce repetitive code
            executeDatabaseStatement("User data inserted successfully", "Failed to insert user data");
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

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
            } finally {
                DatabaseManager.closeConnection();
            }
        }
    }

    /**
     * Executes stored procedures based on provided CallableStatement statement
     * @param successMessage
     * @param failureMessage
     */
    public static void executeDatabaseProcedure(String successMessage, String failureMessage) {
        if (conn != null) {
            try {
                boolean result = callStatement.execute();

                if (!result) {
                    System.out.println(successMessage);
                } else {
                    System.out.println(failureMessage);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } finally {
                DatabaseManager.closeConnection();
            }
        }

    }

    /**
     * Used to insert a food category into the database
     * @param name
     * @param description
     * @param image_url
     */
    public static int insertMeal(String categoryName, String name, String description, String image_url) {
        conn = DatabaseManager.getConnection();
        int meal_id = 0;
        // sets the static connection to the database
        String procedure = "{ call InsertMeal(?, ?, ?, ?) }";
        try
        {
            callStatement = conn.prepareCall(procedure);
            // Set the values for the statement
            callStatement.setString(1, categoryName);
            callStatement.setString(2, name);
            callStatement.setString(3, description);
            callStatement.setString(4, image_url);

            // performs the actual procedure
            // Used only to reduce repetitive code
            executeDatabaseProcedure("Meal successfully inserted into database", "Failure inserting meal into database");

            // the procedure returns the meal_id from the one that was just created
            meal_id = callStatement.getInt(5);
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
        return meal_id;
    }

    public static void insertFood(int meal_id, String name, String currency, double price, Measurement.measurement measurement, double quantity) {
        conn = DatabaseManager.getConnection();
        // sets the static connection to the database
        String procedure = "{ call insertFood(?, ?, ?, ?, ?, ?) }";

        try  {
            callStatement = conn.prepareCall(procedure);
            callStatement.setInt(1, meal_id);
            callStatement.setString(2, name);
            callStatement.setString(3, currency);
            callStatement.setString(4, procedure);
            callStatement.setString(5, (String) "" + measurement);
            callStatement.setDouble(6, quantity);

            executeDatabaseProcedure("Food successfully inserted into database", "Failure to insert food into database");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
    }

    public void insertWholeMeal(ArrayList<Food> foods, String categoryName, String name, String description, String image_url) {
        int meal_id = insertMeal(categoryName, name, description, image_url);
        for (Food food : foods) {
            insertFood(meal_id, food.getName(), food.getCurrency(), food.getPrice(), food.getMeasurement(), food.getQuantity());
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
