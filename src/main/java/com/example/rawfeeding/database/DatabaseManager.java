package com.example.rawfeeding.database;

import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    Properties properties = new Properties();
    DatabaseManager databaseManager = null;
    Connection connection = null;
    String url;
    String username;
    String password;
    private DatabaseManager() {}
    public void loadProperties() {
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

    public void initializeDatabaseConnection() {
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

    public DatabaseManager getInstance() {
        if (databaseManager == null) {
            loadProperties();
            initializeDatabaseConnection();
        }
        return databaseManager;
    }

    public void insertUser(String username, String email, String password) {
        if (connection != null) {

            try
             {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users (username, email, password_hash) VALUES (?, ?, MD5(?))"
                );
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);

            } catch( SQLException sqlException) {
                sqlException.printStackTrace();
            }

        }

    }
}
