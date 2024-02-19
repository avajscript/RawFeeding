package com.example.rawfeeding.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDB {
    /**
     * Used to insert a user into the database
     * @param username
     * @param email
     * @param password
     */
    public static void insertUser(String username, String email, String password) {
        Connection conn;
        PreparedStatement statement;
        // sets the static connection to the database
        String insertQuery = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, MD5(?))";
        try {
            // connect to database and prepare statement
            conn = DatabaseManager.getConnection();
            statement = conn.prepareStatement(insertQuery);


            // Set the values for the statement
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            // performs the actual insert
            // Used only to reduce repetitive code
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully inserted");
            } else {
                System.out.println("User failed to insert");
            }
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}
