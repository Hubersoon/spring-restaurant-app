package com.example.restaurantapp;


import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;


    @RequestMapping(method = RequestMethod.POST, value = "/addRestaurant")
    public boolean addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.addRestaurant(restaurant);
    }

    @RequestMapping("/printAllRestaurant")
    public List<Restaurant> printAllRestaurant() {
        return restaurantService.printAllRestaurant();
    }

    @RequestMapping(value = "/addMeal", method = RequestMethod.PUT)
    public boolean addMeal(@RequestBody Meal meal, @RequestParam UUID id) {
        return restaurantService.addMeal(meal, id);
    }

    @RequestMapping(value = "/printAllMealsForAllRestaurant")
    public Map<Restaurant, List<Meal>> printAllMealsForAllRestaurant() {
        return restaurantService.printAllMealsForAllRestaurant();
    }

    @RequestMapping(value = "/printMealsForSpecificRestaurant")
    public List<Meal> printMealsForSpecificRestaurant(@RequestParam UUID id) {
        return restaurantService.printMealsForSpecificRestaurant(id);
    }

    @RequestMapping(value = "deleteRestaurant", method = RequestMethod.DELETE)
    public boolean deleteRestaurant(@RequestParam UUID id) {
        return restaurantService.deleteRestaurant(id);
    }

    @RequestMapping(value = "/changeName", method = RequestMethod.PUT)
    public void changeRestaurantName(@RequestParam String name, @RequestParam UUID id) {
        restaurantService.changeRestaurantName(name, id);
    }

    @RequestMapping(value = "/deleteMeal", method = RequestMethod.DELETE)
    public boolean deleteMeal(@RequestParam UUID idR, @RequestParam UUID idM) {
        return restaurantService.deleteMeal(idR, idM);
    }

}
