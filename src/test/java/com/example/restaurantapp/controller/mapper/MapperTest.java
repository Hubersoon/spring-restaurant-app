package com.example.restaurantapp.controller.mapper;

import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.model.dto.MealDto;
import com.example.restaurantapp.model.dto.RestaurantDto;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.restaurantapp.controller.mapper.Mapper.map;
import static com.example.restaurantapp.controller.mapper.Mapper.mapListMealsToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapperTest {


    @Test
    public void should_map_CreateMealDto_to_Meal() {
        // given
        var createMealDto = new CreateMealDto("exampleMeal", 100.0);
        // when
        var result = map(createMealDto);
        // then
        assertThat(result.getClass()).isEqualTo(Meal.class);
    }

    @Test
    public void should_map_CreateRestaurantDto_to_Restaurant() {
        // given
        CreateRestaurantDto createRestaurantDto = new CreateRestaurantDto("exampleRestaurant", "warsaw", RestaurantType.ITALIAN);
        // when
        final var result = map(createRestaurantDto);
        // then
        assertThat(result.getClass()).isEqualTo(Restaurant.class);
    }

    @Test
    public void should_map_Restaurant_to_RestaurantDto() {
        // given
        Restaurant restaurant = new Restaurant("exampleRestaurant", "warsaw", RestaurantType.ITALIAN);
        // when
        final var result = map(restaurant);
        // then
        assertThat(result.getClass()).isEqualTo(RestaurantDto.class);
    }

    @Test
    public void should_map_Meal_to_MealDto() {
        // given
        Meal meal = new Meal("example", 100.0);
        // when
        final var result = map(meal);
        // then
        assertThat(result.getClass()).isEqualTo(MealDto.class);
    }

    @Test
    public void should_map_Restaurant_list_to_RestaurantDto() {
        // given
        var listRestaurant = aRestauratantList();
        // when
        final var mapListRestaurant = map(listRestaurant);
        // then
        mapListRestaurant
                .forEach(r -> assertThat(r.getClass()).isEqualTo(RestaurantDto.class));
    }

    @Test
    public void should_map_Meal_list_to_MealDto() {
        // given
        List<Meal> mealList = aMealList();
        // when
        final var mapMealList = mapListMealsToDto(mealList);
        // then
        mapMealList
                .forEach(m -> assertThat(m.getClass()).isEqualTo(MealDto.class));
    }

    @Test
    public void should_throw_runtime_exception_when_parameter_null() {
        // given
        CreateMealDto createMealDto = null;
        // then
        assertThatThrownBy(() -> map(createMealDto)).isInstanceOf(RuntimeException.class);
    }


    private List<Restaurant> aRestauratantList() {
        return List.of(
                new Restaurant("r1", "a1", RestaurantType.AMERICAN),
                new Restaurant("r2", "a2", RestaurantType.ITALIAN),
                new Restaurant("r3", "a3", RestaurantType.TURKISH)
        );
    }

    private List<Meal> aMealList() {
        return List.of(
                new Meal("m1", 100.0),
                new Meal("m2", 200.0),
                new Meal("m3", 300.0)
        );
    }


}
