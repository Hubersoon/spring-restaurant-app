package com.example.restaurantapp.service;


import com.example.restaurantapp.exceptions.NotFoundMealException;
import com.example.restaurantapp.exceptions.NotFoundResturantException;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;


    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public boolean addRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            return false;
        } else {
            return restaurantRepository.addRestaurant(restaurant);
        }
    }

    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.getRestaurantList();
    }

    public boolean addMeal(Meal meal, UUID restaurantId) {
        if (meal == null) {
            return false;
        }
        final var restaurant = findRestaurant(restaurantId);
        return restaurant.addMeal(meal);
    }

    public List<Meal> getRestaurantMeals(UUID id) {
        final var restaurant = findRestaurant(id);
        return restaurant.getMealsList();
    }

    public boolean deleteRestaurant(UUID id) {
        final var restaurant = findRestaurant(id);
        return restaurantRepository.deleteRestaurant(restaurant);
    }

    public Restaurant changeRestaurantName(String name, UUID id) {
        final var restaurant = findRestaurant(id);
        restaurantRepository.deleteRestaurant(restaurant);
        final var newRestaurant = restaurant.updateName(name);
        restaurantRepository.addRestaurant(newRestaurant);
        return newRestaurant;
    }

    public Restaurant changeRestaurantAddress(String address, UUID id) {
        final var restaurant = findRestaurant(id);
        restaurantRepository.restaurantList.remove(restaurant);
        final var newRestaurant = restaurant.updateAddress(address);
        restaurantRepository.restaurantList.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant changeRestaurantType(RestaurantType type, UUID id) {
        final var restaurant = findRestaurant(id);
        restaurantRepository.restaurantList.remove(restaurant);
        final var newRestaurant = restaurant.updateRestaurantType(type);
        restaurantRepository.restaurantList.add(newRestaurant);
        return newRestaurant;
    }

    public boolean deleteMeal(UUID restaurantId, UUID mealId) {
        final var restaurant = findRestaurant(restaurantId);
        final var mealsList = restaurant.getMealsList();
        final var meal = mealsList.stream()
                .filter(m -> m.getId().equals(mealId))
                .findAny()
                .orElseThrow(() -> new NotFoundMealException("Meal doesn't exist"));
        return mealsList.remove(meal);
    }

    private Restaurant findRestaurant(UUID id) {
        return restaurantRepository.getRestaurantList().stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundResturantException("Restaurant doesn't exist in the app"));
    }

}
