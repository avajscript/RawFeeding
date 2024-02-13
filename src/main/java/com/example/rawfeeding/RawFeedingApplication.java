package com.example.rawfeeding;

import com.example.rawfeeding.Formatting.Formatter;
import com.example.rawfeeding.Formatting.JSONFormatter;
import com.example.rawfeeding.Formatting.TextFormatter;
import com.example.rawfeeding.database.DatabaseManager;
import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Meal;
import com.example.rawfeeding.meal.Measurement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SpringBootApplication
public class RawFeedingApplication {
    public static void main(String[] args) {

        Random random = new Random();
        int range = 1000000000;
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        databaseManager.insertCategory("category_name" + random.nextInt(range),
                "category_description" + random.nextInt(range),
                "image_url" + random.nextInt(range));

    }

}
