package com.example.restaurantapp.controller.mapper;

import com.example.restaurantapp.exceptions.NullDtoException;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.model.dto.MealDto;
import com.example.restaurantapp.model.dto.RestaurantDto;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;


public class Mapper {

    public static Restaurant map(CreateRestaurantDto restaurantDTO) {
        validateDto(restaurantDTO);
        return new Restaurant(restaurantDTO.getName(), restaurantDTO.getAddress(), restaurantDTO.getType());
    }

    public static RestaurantDto map(Restaurant restaurant) {
        validateDto(restaurant);
        return RestaurantDto.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .type(restaurant.getType())
                .id(restaurant.getId())
                .mealsList(mapListMealsToDto(restaurant.getMealsList()))
                .build();
    }

    public static Meal map(CreateMealDto mealDto) {
        validateDto(mealDto);
        return new Meal(mealDto.getName(), mealDto.getPrice(),null);
    }

    public static MealDto map(Meal meal) {
        validateDto(meal);
        return MealDto.builder()
                .name(meal.getName())
                .price(meal.price)
                .id(meal.getId())
                .restaurantName(meal.getRestaurant().getName())
                .build();
    }


    public static List<RestaurantDto> map(List<Restaurant> restaurantList) {
        validateDto(restaurantList);
        return restaurantList.stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    public static List<MealDto> mapListMealsToDto(List<Meal> restaurantMeals) {
        validateDto(restaurantMeals);
        return restaurantMeals.stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    private static void validateDto(Object o) {
        if (o == null) {
            throw new NullDtoException("Input object cannot be null");
        }
    }
}
