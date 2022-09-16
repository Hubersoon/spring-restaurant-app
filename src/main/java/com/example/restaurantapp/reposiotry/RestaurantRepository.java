package com.example.restaurantapp.reposiotry;

import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//testowanie
//JUnit
//Mockito
//test dla service, pozytywne i negatywne sciezki
//restAssured
//testowanie funkcyjne


@Repository
public class RestaurantRepository {

    public final List<Restaurant> restaurantList;

    public RestaurantRepository() {
        this.restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("Pizza","Krakow", RestaurantType.ITALIAN));
        restaurantList.add(new Restaurant("Burger's","Kielce", RestaurantType.AMERICAN));
        restaurantList.add(new Restaurant("Kebab","Warszawa", RestaurantType.TURKISH, UUID.fromString("935237ca-01ff-4e32-a212-86853bb48462"),new ArrayList<>()));
    }

    public RestaurantRepository(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }


    public List<Restaurant> getRestaurantList() {
        return this.restaurantList;
    }

    public boolean addRestaurant(Restaurant restaurant) {
        return restaurantList.add(restaurant);
    }
    public boolean deleteRestaurant (Restaurant restaurant){
        return this.restaurantList.remove(restaurant);
        }
}
