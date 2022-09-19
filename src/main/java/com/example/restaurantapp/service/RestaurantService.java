package com.example.restaurantapp.service;


import com.example.restaurantapp.exceptions.NotFoundResturantException;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.UpdateRestaurantRequest;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.reposiotry.MealRepo;
import com.example.restaurantapp.reposiotry.RestaurantRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.restaurantapp.controller.mapper.Mapper.map;

@Service
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;
    private final MealRepo mealRepo;

    public RestaurantService(RestaurantRepo restaurantRepository, MealRepo mealRepo) {
        this.restaurantRepo = restaurantRepository;
        this.mealRepo = mealRepo;
    }


    public ResponseEntity<List<Restaurant>> getAllRestaurant() {
        try {
            List<Restaurant> restaurantList = new ArrayList<>(restaurantRepo.findAll());
            return new ResponseEntity<>(restaurantList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<Restaurant> getSpecificRestaurant(UUID id) {
        final var restaurant = findRestaurant(id);
        try {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<Meal>> getMeals() {
        try {
            List<Meal> mealsList = new ArrayList<>(mealRepo.findAll());
            return new ResponseEntity<>(mealsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<Meal>> getMealsFromSpecificRestaurant(UUID restaurantId) {
        try {
            List<Meal> mealList = new ArrayList<>();
            mealRepo.findAll().stream()
                    .filter(meal -> meal.getRestaurant().getId().equals(restaurantId))
                    .forEach(mealList::add);
            return new ResponseEntity<>(mealList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<Restaurant> addRestaurant(CreateRestaurantDto createRestaurantDto) {
        try {
            Restaurant restaurant = restaurantRepo
                    .save(new Restaurant(createRestaurantDto.getName(), createRestaurantDto.getAddress(), createRestaurantDto.getType()));
            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Meal> addMeal(UUID restaurantId, CreateMealDto createMealDto) {
        Restaurant restaurant = findRestaurant(restaurantId);
        Meal meal = map(createMealDto);
        meal.setRestaurant(restaurant);
        restaurant.addMeal(meal);
        return new ResponseEntity<>(mealRepo.save(meal), HttpStatus.OK);
    }

    public ResponseEntity<Restaurant> updateRestaurant(UUID restaurantId, UpdateRestaurantRequest updateRestaurantRequest) {
        Restaurant restaurant = findRestaurant(restaurantId);
        updateRestaurantRequest.getName().ifPresent(restaurant::setName);
        updateRestaurantRequest.getAddress().ifPresent(restaurant::setAddress);
        updateRestaurantRequest.getType().ifPresent(restaurant::setType);
        return new ResponseEntity<>(restaurantRepo.save(restaurant), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteRestaurant(UUID restaurantId) {
        try {
            restaurantRepo.deleteById(restaurantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<HttpStatus> deleteAllRestaurants() {
        try {
            restaurantRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<HttpStatus> deleteMeal(UUID mealId) {
        try {
            mealRepo.deleteById(mealId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteMealsFromSpecificRestaurant(UUID restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);
        try {
            mealRepo.findAll().stream()
                    .filter(m -> m.getRestaurant().equals(restaurant))
                    .forEach(mealRepo::delete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ResponseEntity<HttpStatus> deleteMeals() {
        try {
            mealRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Restaurant findRestaurant(UUID restaurantId) {
        return restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundResturantException("Restaurant not found"));
    }
}
