package com.example.restaurantapp.reposiotry;

import com.example.restaurantapp.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepo extends JpaRepository<Restaurant, UUID> {


}
