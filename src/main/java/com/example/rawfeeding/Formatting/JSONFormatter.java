package com.example.rawfeeding.Formatting;

import com.example.rawfeeding.meal.Food;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONFormatter implements Formatter{

    @Override
    public String formatFood(Food food) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(food);
        } catch(JsonProcessingException e) {
            throw new IOException(e);
        }
    }
}
