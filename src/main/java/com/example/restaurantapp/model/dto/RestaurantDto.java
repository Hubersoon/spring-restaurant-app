package com.example.restaurantapp.model.dto;

import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class RestaurantDto {

    private final String name;
    private final String address;
    private final RestaurantType type;
    private final UUID id;
    private final List<Meal> mealsList;

}
