package com.example.restaurantapp.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Meal {

    public final UUID id;
    public final String name;
    public final Double price;

    public Meal(String name, Double price) {
        this.name = name;
        this.price = price;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Meal ID: " + id +
                ", name: " + name +
                ", price: " + price
                ;
    }
}
