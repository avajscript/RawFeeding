package com.example.rawfeeding.Formatting;

import com.example.rawfeeding.meal.Food;

import java.io.IOException;

public interface Formatter {
    public String formatFood(Food food) throws IOException;
}
