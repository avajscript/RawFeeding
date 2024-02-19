package com.example.rawfeeding.meal;

import jakarta.persistence.*;

@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_id")
    private Long id;
    protected String name;
    protected double quantity;
    protected Measurement.measurement measurement;
    protected String currency;
    protected double price;
    public Food() {}
    public Food(String name, double quantity, Measurement.measurement measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal associatedMeal;
    // Getters and setters
    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Measurement.measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement.measurement measurement) {
        this.measurement = measurement;
    }
    // end of getters and setters

    public String toString() {
        return "name: " + name + ", quantity: " + quantity + ", measurement: " + measurement;
    }
}
