package com.example.restaurantapp.service;


import com.example.restaurantapp.exceptions.NotFoundMealException;
import com.example.restaurantapp.exceptions.NotFoundResturantException;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService() {
        this.restaurantRepository = new RestaurantRepository();
        restaurantRepository.restaurantList.add(new Restaurant("Kebab","Warszawa", RestaurantType.TURKISH));
        restaurantRepository.restaurantList.add(new Restaurant("Pizza","Krakow", RestaurantType.ITALIAN));
        restaurantRepository.restaurantList.add(new Restaurant("Burger's","Kielce", RestaurantType.AMERICAN));

    }

    public boolean addRestaurant(Restaurant restaurant) {
        Restaurant newRestaurant = restaurant;
      return restaurantRepository.restaurantList.add(newRestaurant);
    }

    public List<Restaurant> printAllRestaurant() {
        return restaurantRepository.restaurantList;
    }

    public boolean addMeal(Meal meal, UUID id) {
        final var restaurant = findRestaurant(id);
        return restaurant.addMeal(meal);
    }

    public Map<Restaurant, List<Meal>> printAllMealsForAllRestaurant() {
        Map<Restaurant,List<Meal>> allMeals = new HashMap<>();
        restaurantRepository.restaurantList.stream()
                .forEach(restaurant -> allMeals.put(restaurant,restaurant.mealsList));
        return allMeals;
    }

    public List<Meal> printMealsForSpecificRestaurant(UUID id) {
        final var restaurant = findRestaurant(id);
        return restaurant.getMealsList();
    }

    public boolean deleteRestaurant(UUID id) {
        final var restaurant = findRestaurant(id);
       return restaurantRepository.restaurantList.remove(restaurant);
    }

    private Restaurant findRestaurant(UUID id) {
       return restaurantRepository.restaurantList.stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findAny()
                .orElseThrow(NotFoundResturantException::new);
    }

    public void changeRestaurantName(String name, UUID id) {
        final var restaurant = findRestaurant(id);
        restaurantRepository.restaurantList.remove(restaurant);
        final var newRestaurant = restaurant.updateName(name);
        restaurantRepository.restaurantList.add(newRestaurant);
    }

    public boolean deleteMeal(UUID idR, UUID idM) {
        final var restaurant = findRestaurant(idR);
        final var mealsList = restaurant.getMealsList();
        final var meal = mealsList.stream()
                .filter(m -> m.getId().equals(idM))
                .findAny()
                .orElseThrow(NotFoundMealException::new);
       return mealsList.remove(meal);
    }
}
