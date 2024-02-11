package com.example.rawfeeding;

import com.example.rawfeeding.Formatting.Formatter;
import com.example.rawfeeding.Formatting.JSONFormatter;
import com.example.rawfeeding.Formatting.TextFormatter;
import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Meal;
import com.example.rawfeeding.meal.Measurement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class RawFeedingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RawFeedingApplication.class, args);
        Formatter jsonFormatter = new JSONFormatter();
        Formatter formatter = new TextFormatter();
        System.out.println("Hello ");



    }

}
