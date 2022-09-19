package com.example.restaurantapp.reposiotry;

import com.example.restaurantapp.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealRepo extends JpaRepository<Meal, UUID> {
}
