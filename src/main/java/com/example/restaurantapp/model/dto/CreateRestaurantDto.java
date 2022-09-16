package com.example.restaurantapp.model.dto;

import com.example.restaurantapp.model.RestaurantType;
import lombok.*;

@Data
@AllArgsConstructor
//@RequiredArgsConstructor
@Builder
public class CreateRestaurantDto {

    private final String name;
    private final String address;
    private final RestaurantType type;
}
