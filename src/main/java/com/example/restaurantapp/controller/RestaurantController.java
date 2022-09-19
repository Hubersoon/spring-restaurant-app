package com.example.restaurantapp.controller;


import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.UpdateRestaurantRequest;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurant() {
        return restaurantService.getAllRestaurant();
    }

    @GetMapping("restaurants/{id}")
    public ResponseEntity<Restaurant> getSpecificRestaurant(@PathVariable("id") UUID id) {
        return restaurantService.getSpecificRestaurant(id);
    }

    @GetMapping("restaurants/{id}/meals")
    public ResponseEntity<List<Meal>> getMealsForSpecificRestaurant(@PathVariable("id") UUID restaurantId) {
        return restaurantService.getMealsFromSpecificRestaurant(restaurantId);
    }

    @GetMapping("restaurants/meals")
    public ResponseEntity<List<Meal>> getMeals() {
        return restaurantService.getMeals();
    }

    @PostMapping("restaurants")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody CreateRestaurantDto restaurant) {
        return restaurantService.addRestaurant(restaurant);
    }

    @PostMapping("restaurants/{id}/meals")
    public ResponseEntity<Meal> addMeal(@PathVariable("id") UUID restaurantId,
                                        @RequestBody CreateMealDto createMealDto) {

        return restaurantService.addMeal(restaurantId, createMealDto);
    }

    @PatchMapping("restaurants/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody UpdateRestaurantRequest updateRestaurantRequest,
                                                       @PathVariable("id") UUID restaurantId) {
        return restaurantService.updateRestaurant(restaurantId, updateRestaurantRequest);
    }

    @DeleteMapping("restaurants")
    public ResponseEntity<HttpStatus> deleteAllRestaurants() {
        return restaurantService.deleteAllRestaurants();
    }

    @DeleteMapping("restaurants/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable("id") UUID restaurantId) {
        return restaurantService.deleteRestaurant(restaurantId);
    }

    @DeleteMapping("/restaurants/{restaurantId}/meals/")
    public ResponseEntity<HttpStatus> deleteMealsFromSpecificRestaurant(@PathVariable("restaurantId") UUID restaurantId) {
        return restaurantService.deleteMealsFromSpecificRestaurant(restaurantId);
    }

    @DeleteMapping("/restaurants/meals/{mealId}")
    public ResponseEntity<HttpStatus> deleteMeal(@PathVariable("mealId") UUID mealId) {
        return restaurantService.deleteMeal(mealId);
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteMeals() {
        return restaurantService.deleteMeals();
    }

}

