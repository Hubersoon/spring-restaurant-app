package com.example.restaurantapp.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class MealDto {

    public final String restaurantName;
    public final UUID id;
    public final String name;
    public final Double price;
}
