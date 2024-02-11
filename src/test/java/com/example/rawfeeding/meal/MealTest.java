package com.example.rawfeeding.meal;

import com.example.rawfeeding.Formatting.Formatter;
import com.example.rawfeeding.Formatting.JSONFormatter;
import com.example.rawfeeding.Formatting.TextFormatter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {
    Formatter jsonFormatter = new JSONFormatter();
    @Test
    public void outputFoods() {
        // construct the foods
       Food food = new Food("ground beef", 5, Measurement.measurement.LB),
               food2 = new Food("turkey", 5, Measurement.measurement.KG),
               food3 = new Food("eggs", 1, Measurement.measurement.DOZEN);
        ArrayList<Food> foods = new ArrayList<>(Arrays.asList(food, food2, food3));

        // construct the meal
        Meal meal = new Meal(foods);
        try {
            System.out.println(meal.getAllFoods(jsonFormatter));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}