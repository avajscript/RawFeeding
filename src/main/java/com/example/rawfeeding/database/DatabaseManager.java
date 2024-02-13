package com.example.rawfeeding.database;

import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static Properties properties = new Properties();
    private static DatabaseManager databaseManager = null;
    private static Connection connection = null;
    private static String url;
    private static String username;
    private static String password;
    private DatabaseManager() {}
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

    public static DatabaseManager getInstance() {
        // if databaseManager does not exist yet
        if (databaseManager == null) {
            // loads the properties for the db connection from database.properties file
            loadProperties();
            // sets the static connecton to the database
            //initializeDatabaseConnection();
            databaseManager = new DatabaseManager();
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

    public String getProperties() {
        return "url= " + url + ", username" + username + ", password=" + password;
    }
}
