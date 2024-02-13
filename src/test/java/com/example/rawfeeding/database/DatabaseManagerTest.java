package com.example.rawfeeding.database;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    @Test
    public void testInsertUser() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.insertUser("poophead", "poop@gmail.com", "123123123");
    }

    @Test
    public void testInsertCategory() {
        Random random = new Random();
        int range = 1000000000;
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.insertCategory("category_name" + random.nextInt(range),
                "category_description" + random.nextInt(range),
                "image_url" + random.nextInt(range));
    }

    @Test
    public void testProperties() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        System.out.println(databaseManager.getProperties());
    }

    @Test
    public void testInsertMeal() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.insertMeal("beef", "ground beef", "ground beef description", "beef.png");
    }
}