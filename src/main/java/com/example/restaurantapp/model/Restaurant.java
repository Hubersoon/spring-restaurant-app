package com.example.restaurantapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
public class Restaurant {

    public final String name;
    public final String address;
    public final RestaurantType type;
    public final UUID id;
    public final List<Meal> mealsList;

    public Restaurant(String name, String address, RestaurantType type) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.id = UUID.randomUUID();
        this.mealsList = new ArrayList<>();
    }

    public boolean addMeal(Meal meal) {
        return this.mealsList.add(meal);
    }

    public UUID getId() {
        return this.id;
    }

    public List<Meal> getMealsList() {
        return mealsList;
    }

    public void printMealList(){
        for (int i = 0; i < mealsList.size(); i++) {
            System.out.println(mealsList.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public Restaurant updateName(String name) {
        return Restaurant.builder()
                .name(name)
                .address(address)
                .type(type)
                .id(id)
                .mealsList(mealsList)
                .build();
    }
    public Restaurant updateMealList(List<Meal> mealsList) {
        return Restaurant.builder()
                .name(name)
                .address(address)
                .type(type)
                .id(id)
                .mealsList(mealsList)
                .build();
    }


    @Override
    public String toString() {
        return
                "id: " + id +
                        ", name: \"" + name + '\"' +
                        ", address: \"" + address + '\"' +
                        ", type: " + type
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;

        Restaurant that = (Restaurant) o;

        return getId().equals(that.getId());
    }
    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}


