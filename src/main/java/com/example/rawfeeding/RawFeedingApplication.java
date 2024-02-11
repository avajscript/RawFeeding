package com.example.rawfeeding;

import com.example.rawfeeding.meal.Food;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RawFeedingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RawFeedingApplication.class, args);
        System.out.println("Hello ");

    }

}
