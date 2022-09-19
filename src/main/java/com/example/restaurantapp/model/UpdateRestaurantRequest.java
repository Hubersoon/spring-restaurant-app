package com.example.restaurantapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateRestaurantRequest {

    private Optional<String> name = empty();
    private Optional<String> address = empty();
    private Optional<RestaurantType> type = empty();
    private Optional<List<Meal>> mealList = empty();

}
