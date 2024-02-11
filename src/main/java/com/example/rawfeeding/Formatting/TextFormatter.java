package com.example.rawfeeding.Formatting;

import com.example.rawfeeding.meal.Food;

import java.io.IOException;

public class TextFormatter implements Formatter{

    @Override
    public String formatFood(Food food) throws IOException {
        return food.toString();
    }
}
