package com.example.rawfeeding.database;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UserDBTest {
    @Test
    public void testInsertUser() {
        int maxRand = 25;
        Random random = new Random();
        int randomNum = random.nextInt(maxRand);
        UserDB.insertUser("username" + randomNum, "testemail" + randomNum + "@gmail.com", "password123");
        System.out.println("Inserting user test performed");
    }
}