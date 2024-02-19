package com.example.rawfeeding.database;

import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Meal;
import com.example.rawfeeding.meal.Measurement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DBOperationsTest {
    @Test
    public void testInsertMeal() {
        Meal meal = new Meal();
        meal.setName("chicken and fish");
        meal.setDescription("Chicken and fish description");
        meal.setImage_url("meal.png");
        int id = FoodDB.insertMeal("beef", meal);
        System.out.println("Added meal test performed, id=" + id);
    }
    @Test
    public void testInsertWholeMeal() {
        Meal meal = new Meal();
        meal.addFood(new Food("ground beef", 5, Measurement.measurement.LB));
        meal.addFood(new Food("sardine", 3, Measurement.measurement.LB));
        meal.setName("beef and sardines");
        FoodDB.insertWholeMeal(meal,"beef");
        System.out.println("Add whole meal test performed");
    }

    @Test
    public void testInsertCategory() {
        DBOperations.insertCategory("beef", "Beef products", "beef.png");
        System.out.println("Add category test performed");
    }

    @Test
    public void testInsertFood() {
        Food food = new Food("fishes", 5, Measurement.measurement.LB);
        FoodDB.insertFood(9, food);
        System.out.println("Inserting food test performed");
    }
}