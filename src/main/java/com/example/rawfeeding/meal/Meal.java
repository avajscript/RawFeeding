package com.example.rawfeeding.meal;

import java.util.ArrayList;

public class Meal {
    ArrayList<Food> foods;

    public Meal() {
        foods = new ArrayList<Food>();
    }

    public Meal(ArrayList<Food> foods) {
        this.foods = foods;
    }
}
