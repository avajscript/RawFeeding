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
    private static String url = null;
    private static String username = null;
    private static String password = null;
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
    public static Connection getConnection() {
        try {
            // only create a new connection if there isn't one already
            if (connection == null || connection.isClosed()) {
                // load properties if they haven't been set yet
                if (url == null) {
                    loadProperties();
                }
                connection = DriverManager.getConnection(url, username, password);
            } else {
                System.out.println("Cannot create new connection, using existing one");
            }

        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }


    /**
     * Close the connection if it isn't null and
     * the connect isn't already closed
     */
    public static void closeConnection() {
        try {
            // only close the connection if it exists
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Testing function to return the database properties
     * @return
     */
    public static String getProperties() {
        return "url= " + url + ", username" + username + ", password=" + password;
    }
}
