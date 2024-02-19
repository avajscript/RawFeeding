package com.example.rawfeeding.controllers;

import com.example.rawfeeding.meal.Meal;
import com.example.rawfeeding.meal.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    @ResponseBody
    public List<Meal> getAllMeals(Model model) {
        /*
        List<Meal> meals = mealService.getAllMeals();
        model.addAttribute("meals", meals);
        return "meals";
         */
        List<Meal> meals = mealService.getAllMeals();
        return meals;
    }

    @PostMapping("/meals")
    @ResponseBody
    public Meal saveMeal(@Validated @RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }
}
