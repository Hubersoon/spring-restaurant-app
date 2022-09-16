package com.example.restaurantapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CreateMealDto {
    public final String name;
    public final Double price;
}
