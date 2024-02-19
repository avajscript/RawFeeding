package com.example.rawfeeding.database;

import com.example.rawfeeding.meal.Food;
import com.example.rawfeeding.meal.Meal;
import com.example.rawfeeding.meal.Measurement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class FoodDB {

    /**
     * Used to insert a food category into the database
     * @param meal the meal to be added
     */
    public static int insertMeal(String categoryName, Meal meal) {
        Connection conn = DatabaseManager.getConnection();
        CallableStatement callStatement;
        int meal_id = 0;
        // sets the static connection to the database
        String procedure = "{ call insertMeal(?, ?, ?, ?, ?) }";
        try
        {
            callStatement = conn.prepareCall(procedure);
            // Set the values for the statement
            callStatement.setString(1, categoryName);
            callStatement.setString(2, meal.getName() );
            callStatement.setString(3, meal.getDescription());
            callStatement.setString(4, meal.getImage_url());
            callStatement.registerOutParameter(5, Types.INTEGER);

            boolean result = callStatement.execute();
            if (!result) {
                System.out.println("Meal successfully inserted into database");
            } else {
                System.out.println("Meal failed to insert into database");
            }
            // the procedure returns the meal_id from the one that was just created
            meal_id = callStatement.getInt(5);
        } catch( SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
        return meal_id;
    }
    public static void insertFood(int meal_id, Food food) {
        Connection conn = DatabaseManager.getConnection();
        CallableStatement callStatement;
        // sets the static connection to the database
        String procedure = "{ call insertFood(?, ?, ?, ?, ?, ?) }";

        try  {
            // prepared statement
            callStatement = conn.prepareCall(procedure);
            callStatement.setInt(1, meal_id);
            callStatement.setString(2, food.getName());
            callStatement.setString(3, food.getCurrency());
            callStatement.setDouble(4, food.getPrice());
            callStatement.setString(5, (String) "" + food.getMeasurement());
            callStatement.setDouble(6, food.getQuantity());

            // insert into database
            boolean result = callStatement.execute();
            // output the status of the insert
            if  (!result) {
                System.out.println("Food successfully inserted into database");
            } else {
                System.out.println("Food failed to insert into database");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
    }

    public static void insertWholeMeal(Meal meal, String categoryName) {
        int meal_id = insertMeal(categoryName, meal);
        for (Food food : meal.getFoods()) {
            insertFood(meal_id, food);
        }
    }

}
