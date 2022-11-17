package com.example.restaurantapp.service;


import com.example.restaurantapp.controller.mapper.Mapper;
import com.example.restaurantapp.exceptions.NotFoundMealException;
import com.example.restaurantapp.exceptions.NotFoundResturantException;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.UpdateRestaurantRequest;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.model.dto.MealDto;
import com.example.restaurantapp.model.dto.RestaurantDto;
import com.example.restaurantapp.reposiotry.MealReposiotry;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.restaurantapp.controller.mapper.Mapper.map;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MealReposiotry mealReposiotry;

    public RestaurantService(RestaurantRepository restaurantRepository, MealReposiotry mealReposiotry) {
        this.restaurantRepository = restaurantRepository;
        this.mealReposiotry = mealReposiotry;
    }

    public List<RestaurantDto> getRestaurants() {
        final var restaurantList = new ArrayList<RestaurantDto>();
        restaurantRepository.findAll()
                .forEach(restaurant -> restaurantList.add(map(restaurant)));
        return restaurantList;
    }

    public RestaurantDto getRestaurant(UUID id) {
        return map(findRestaurant(id));
    }

    public List<MealDto> getMeals() {
        final var mealsListDto = new ArrayList<MealDto>();
        mealReposiotry.findAll().forEach(meal -> mealsListDto.add(map(meal)));
        return mealsListDto;
    }

    public List<MealDto> getMealsFromRestaurant(UUID restaurantId) {
        final var result = mealReposiotry.findAll().stream()
                .filter(meal -> meal.getRestaurant().getId().equals(restaurantId))
                .map(Mapper::map)
                .collect(Collectors.toList());
        return result;
    }

    public RestaurantDto addRestaurant(CreateRestaurantDto createRestaurantDto) {
        final var restaurant = restaurantRepository
                .save(map(createRestaurantDto));
        return map(restaurant);

    }

    public MealDto addMeal(UUID restaurantId, CreateMealDto createMealDto) {
        final var meal = map(createMealDto);
        final var restaurant = findRestaurant(restaurantId);
        meal.setRestaurant(restaurant);
        restaurant.getMealsList().add(meal);
        mealReposiotry.save(meal);
        return map(meal);
    }

    public RestaurantDto updateRestaurant(UUID restaurantId, UpdateRestaurantRequest updateRestaurantRequest) {
        final var restaurant = findRestaurant(restaurantId);
        updateRestaurantRequest.getName().ifPresent(restaurant::setName);
        updateRestaurantRequest.getAddress().ifPresent(restaurant::setAddress);
        updateRestaurantRequest.getType().ifPresent(restaurant::setType);
        restaurantRepository.save(restaurant);
        return map(restaurant);
    }

    public void deleteRestaurant(UUID restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    public void deleteAllRestaurants() {
        restaurantRepository.deleteAll();
    }

    public void deleteMeal(UUID mealId) {
        final var meal = findMeal(mealId);
        final var restaurant = meal.getRestaurant();
        restaurant.getMealsList().remove(meal);
        mealReposiotry.deleteById(mealId);
        restaurantRepository.save(restaurant);
    }

    public void deleteMealsFromRestaurant(UUID restaurantId) {
        final var restaurant = findRestaurant(restaurantId);
        restaurant.getMealsList().clear();
        mealReposiotry.findAll().stream()
                .filter(meal -> meal.getRestaurant().equals(restaurant))
                .forEach(mealReposiotry::delete);
        restaurantRepository.save(restaurant);
    }

    public void deleteMeals() {
        final var restaurants = new ArrayList<>(restaurantRepository.findAll());
        mealReposiotry.deleteAll();
        restaurants
                .forEach(restaurant -> {
                    restaurant.getMealsList().clear();
                    restaurantRepository.save(restaurant);
                });
    }

    public Restaurant findRestaurant(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundResturantException("Restaurant not found"));
    }

    private Meal findMeal(UUID mealId) {
        return mealReposiotry.findById(mealId)
                .orElseThrow(() -> new NotFoundMealException("Meal not found"));
    }

}
