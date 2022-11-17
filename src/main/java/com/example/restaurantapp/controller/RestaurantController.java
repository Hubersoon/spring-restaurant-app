package com.example.restaurantapp.controller;


import com.example.restaurantapp.model.UpdateRestaurantRequest;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.model.dto.MealDto;
import com.example.restaurantapp.model.dto.RestaurantDto;
import com.example.restaurantapp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("restaurants")
    public ResponseEntity<List<RestaurantDto>> getRestaurants() {
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping("restaurants/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(restaurantService.getRestaurant(id), HttpStatus.OK);
    }

    @GetMapping("restaurants/{id}/meals")
    public ResponseEntity<List<MealDto>> getMealsFromRestaurant(@PathVariable("id") UUID restaurantId) {
        return new ResponseEntity<>(restaurantService.getMealsFromRestaurant(restaurantId), HttpStatus.OK);
    }

    @GetMapping("restaurants/meals")
    public ResponseEntity<List<MealDto>> getMeals() {
        return new ResponseEntity<>(restaurantService.getMeals(), HttpStatus.OK);
    }

    @PostMapping("restaurants")
    public ResponseEntity<RestaurantDto> addRestaurant(@RequestBody CreateRestaurantDto restaurant) {
        return new ResponseEntity<>(restaurantService.addRestaurant(restaurant), HttpStatus.CREATED);
    }

    @PostMapping("restaurants/{id}/meals")
    public ResponseEntity<MealDto> addMeal(@PathVariable("id") UUID restaurantId,
                                           @RequestBody CreateMealDto createMealDto) {
        return new ResponseEntity<>(restaurantService.addMeal(restaurantId, createMealDto), HttpStatus.CREATED);
    }

    @PatchMapping("restaurants/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody UpdateRestaurantRequest updateRestaurantRequest,
                                                          @PathVariable("id") UUID restaurantId) {
        final var restaurantDto = restaurantService.updateRestaurant(restaurantId, updateRestaurantRequest);
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }

    @DeleteMapping("restaurants")
    public ResponseEntity<HttpStatus> deleteAllRestaurants() {
        restaurantService.deleteAllRestaurants();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("restaurants/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable("id") UUID restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("restaurants/{restaurantId}/meals")
    public ResponseEntity<HttpStatus> deleteMealsFromRestaurant(@PathVariable("restaurantId") UUID restaurantId) {
        restaurantService.deleteMealsFromRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("restaurants/meals/{mealId}")
    public ResponseEntity<HttpStatus> deleteMeal(@PathVariable("mealId") UUID mealId) {
        restaurantService.deleteMeal(mealId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("restaurants/meals")
    public ResponseEntity<HttpStatus> deleteMeals() {
        restaurantService.deleteMeals();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

