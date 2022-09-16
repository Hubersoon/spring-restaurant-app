package com.example.restaurantapp.model;

import lombok.Data;

import java.util.UUID;

@Data
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





}
