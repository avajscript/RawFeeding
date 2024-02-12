package com.example.rawfeeding.database;

import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private Properties properties = new Properties();
    private static DatabaseManager databaseManager = null;
    private static Connection connection = null;
    private String url;
    private String username;
    private String password;
    private DatabaseManager() {}
    public void static loadProperties() {
        try (InputStream input  = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
                return;
            }

            // Load the properties file
            properties.load(input);
            // set the class attributes from the properties
            url = properties.getProperty("jbdc.url");
            username = properties.getProperty("jbdc.username");
            password = properties.getProperty("jbdc.password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties");
        }
    }

    public static void initializeDatabaseConnection() {
        try (
                Connection localConnection = DriverManager.getConnection(url, username, password);
                ) {
            connection = localConnection;
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            initializeDatabaseConnection();
        }
        return connection;
    }

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            loadProperties();
            initializeDatabaseConnection();
            databaseManager = this;
        }
        return databaseManager;
    }

    public void insertUser(String username, String email, String password) {
        if (connection != null) {
            String insertQuery = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, MD5(?))";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery))
             {
                // Set the values for the statement
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);

                // Execute the query
                 int rowsAffected = statement.executeUpdate();
                 if (rowsAffected > 0) {
                     System.out.println("Data inserted successfully");
                 } else {
                    System.out.println("Failed to insert data");
                 }
            } catch( SQLException sqlException) {
                sqlException.printStackTrace();
            }

        }

    }
}
