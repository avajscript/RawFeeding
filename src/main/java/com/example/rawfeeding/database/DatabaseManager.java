package com.example.rawfeeding.database;

import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Measurement;

import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class DatabaseManager {
    private static Properties properties = new Properties();
    private static DatabaseManager databaseManager = null;
    private static Connection connection = null;
    private static String url;
    private static String username;
    private static String password;
    private DatabaseManager() {}

    /**
     * Loads the properties for connection to the database from the database.properties file
     */
    public static void loadProperties() {
        try (InputStream input  = DatabaseManager.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
                return;
            }

            // Load the properties file
            properties.load(input);
            // set the class attributes from the properties
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties");
        }
    }

    /**
     * Initializes the database if it does not yet exist.
     * Sets the static connection variable
     */
    public static void initializeDatabaseConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }

        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    /**
     * Close the connection if it isn't null and
     * the connect isn't already closed
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a static instance of the database to ensure the database object
     * isn't duplicated
     * @return DatabaseManager static instance
     */
    public static DatabaseManager getInstance() {
        // if databaseManager does not exist yet
        if (databaseManager == null) {
            // loads the properties for the db connection from database.properties file
            loadProperties();
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    /**
     * Used to insert a user into the database
     * @param username
     * @param email
     * @param password
     */
    public void insertUser(String username, String email, String password) {
        initializeDatabaseConnection();
        // sets the static connection to the database
            String insertQuery = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, MD5(?))";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery))
             {
                // Set the values for the statement
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                // performs the actual insert
                 // Used only to reduce repetitive code
                executeDatabaseStatement(statement, "User data inserted successfully", "Failed to insert user data");
            } catch( SQLException sqlException) {
                sqlException.printStackTrace();
            }

    }

    public void executeDatabaseStatement(PreparedStatement statement, String successMessage, String failureMessage) {
        // sets the static connection to the database
        if (connection != null) {
            try  {
                // Execute the query
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(successMessage);
                } else {
                    System.out.println(failureMessage);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * Executes stored procedures based on provided CallableStatement statement
     * @param statement
     * @param successMessage
     * @param failureMessage
     */
    public void executeDatabaseProcedure(CallableStatement statement, String successMessage, String failureMessage) {
            if (connection != null) {
                try {
                    boolean result = statement.execute();

                    if (!result) {
                        System.out.println(successMessage);
                    } else {
                        System.out.println(failureMessage);
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                } finally {
                    closeConnection();
                }
            }

    }

    /**
     * Used to insert a food category into the database
     * @param name
     * @param description
     * @param image_url
     */
    public int insertMeal(String categoryName, String name, String description, String image_url) {
        initializeDatabaseConnection();
        int meal_id = 0;
        // sets the static connection to the database
        String procedure = "{ call InsertMeal(?, ?, ?, ?) }";
        try (CallableStatement statement = connection.prepareCall(procedure))
        {
            // Set the values for the statement
            statement.setString(1, categoryName);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setString(4, image_url);

            // performs the actual procedure
            // Used only to reduce repetitive code
            executeDatabaseProcedure(statement, "Meal successfully inserted into database", "Failure inserting meal into database");

            // the procedure returns the meal_id from the one that was just created
            meal_id = statement.getInt(5);
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return meal_id;
    }

    public void insertFood(int meal_id, String name, String currency, double price, Measurement.measurement measurement, double quantity) {
        initializeDatabaseConnection();
        // sets the static connection to the database
        String procedure = "{ call insertFood(?, ?, ? ?, ?, ?) }";

        try (CallableStatement statement = connection.prepareCall(procedure)) {
            statement.setInt(1, meal_id);
            statement.setString(2, name);
            statement.setString(3, currency);
            statement.setString(4, procedure);
            statement.setString(5, (String) "" + measurement);
            statement.setInt(16, quantity);

            executeDatabaseProcedure(statement, "Food successfully inserted into database", "Failure to insert food into database");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void insertWholeMeal(ArrayList<Food> foods, String categoryName, String name, String description, String image_url) {
        int meal_id = insertMeal(categoryName, name, description, image_url);
        for (Food food : foods) {
            insertFood(meal_id, food.getName(), food.getCurrency(), food.getPrice(), food.getMeasurement(), food.getQuantity());
        }
    }

    public void insertCategory(String name, String description, String image_url) {
        initializeDatabaseConnection();
        // sets the static connection to the database
        String insertQuery = "INSERT INTO food_categories (name, description, image_url) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery))
        {
            // Set the values for the statement
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, image_url);
            // performs the actual insert
            // Used only to reduce repetitive code
            executeDatabaseStatement(statement, "Food category data inserted successfully", "Failed to insert food category data");
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Testing function to return the database properties
     * @return
     */
    public String getProperties() {
        return "url= " + url + ", username" + username + ", password=" + password;
    }
}
