package com.example.rawfeeding.meal;

import com.example.rawfeeding.Formatting.Formatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used for generating meals, where u can add different foods to it
 */
public class Meal {
    private ArrayList<Food> foods;
    private String description;

    public Meal() {
        foods = new ArrayList<Food>();
    }

    public Meal(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public Meal(ArrayList<Food> foods, String description) {
        this(foods);
        this.description = description;
    }

    /**
     * Add a new food to food list
     * @param food
     */
    public void addFood(Food food) {
        foods.add(food);
    }

    /**
     * Remove the food given as a parameter from the food list
     * @param food
     */
    public void removeFood(Food food) {
        String foodName = food.getName();
        // iterator over every food and remove the one the user specified
        for (Food f : foods) {
            if(f.getName().equals(foodName)) {
                foods.remove(f);
                break;
            }
        }
    }

    /**
     * Returns a string array of all foods in the meal based on the given formatter
     * @param formatter the formatter that specifies what type of format to return
     * @return
     * @throws IOException
     */
    public String[] getAllFoods(Formatter formatter) throws IOException {
        // the array to return
        String[] foodList = new String[foods.size()];
        try {
            // iterate over every food, format and add to the food list
            for (int i = 0; i < foods.size(); i++) {
                foodList[i] = formatter.formatFood(foods.get(i));
            }
            return foodList;
        } catch (JsonProcessingException e) {
            throw new IOException(e);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
