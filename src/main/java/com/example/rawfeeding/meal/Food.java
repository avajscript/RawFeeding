package com.example.rawfeeding.meal;

public class Food {
    protected String name;
    protected double quantity;
    protected Measurement.measurement measurement;

    public Food(String name, double quantity, Measurement.measurement measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }
    // Getters and setters
    public String getName() {
        return name;
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
