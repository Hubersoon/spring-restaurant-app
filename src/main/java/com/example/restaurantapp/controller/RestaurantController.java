package com.example.restaurantapp.controller;


import com.example.restaurantapp.controller.mapper.Mapper;
import com.example.restaurantapp.model.RestaurantType;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.model.dto.MealDto;
import com.example.restaurantapp.model.dto.RestaurantDto;
import com.example.restaurantapp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.restaurantapp.controller.mapper.Mapper.map;
import static com.example.restaurantapp.controller.mapper.Mapper.mapListMealsToDto;

@RequestMapping("api")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("restaurants")
    public boolean addRestaurant(@RequestBody CreateRestaurantDto restaurant) {
        return restaurantService.addRestaurant(Mapper.map(restaurant));
    }

    @GetMapping("restaurants")
    public List<RestaurantDto> printAllRestaurant() {
        return Mapper.map(restaurantService.getAllRestaurant());
    }

    @PostMapping("restaurants/{id}/meals")
    public boolean addMeal(@RequestBody CreateMealDto mealDto, @PathVariable("id") UUID id) {
        return restaurantService.addMeal(map(mealDto), id);
    }

    @GetMapping("restaurants/{id}/meals")
    public List<MealDto> printMealsForSpecificRestaurant(@PathVariable("id") UUID id) {
        return mapListMealsToDto(restaurantService.getRestaurantMeals(id));
    }

    @DeleteMapping("/restaurants/{id}")
    public boolean deleteRestaurant(@PathVariable("id") UUID id) {
        return restaurantService.deleteRestaurant(id);
    }

    @PatchMapping("restaurants/{id}")
    public void changeRestaurantData(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String address,
                                     @RequestParam(required = false) RestaurantType type,
                                     @PathVariable("id") UUID id) {
        if (name != null) {
            restaurantService.changeRestaurantName(name, id);
        }
        if (address != null) {
            restaurantService.changeRestaurantAddress(address, id);
        }
        if (type != null) {
            restaurantService.changeRestaurantType(type, id);
        }
    }

    @DeleteMapping("/restaurants/{restaurantId}/meals/{mealId}")
    public boolean deleteMeal(@PathVariable UUID restaurantId, @PathVariable UUID mealId) {
        return restaurantService.deleteMeal(restaurantId, mealId);
    }

}

