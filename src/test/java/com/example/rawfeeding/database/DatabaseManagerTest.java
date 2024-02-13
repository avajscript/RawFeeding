package com.example.rawfeeding.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    @Test
    public void testInsertUser() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.insertUser("poophead", "poop@gmail.com", "123123123");
    }

    @Test
    public void testProperties() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        System.out.println(databaseManager.getProperties());
    }
}