package com.example.restaurantapp.controller.mapper;

import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.model.dto.MealDto;
import com.example.restaurantapp.model.dto.RestaurantDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.restaurantapp.controller.mapper.Mapper.map;
import static com.example.restaurantapp.controller.mapper.Mapper.mapListMealsToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class MapperTest {


    private static List<Restaurant> aRestauratantList() {
        return List.of(
                new Restaurant("r1", "a1", RestaurantType.AMERICAN),
                new Restaurant("r2", "a2", RestaurantType.ITALIAN),
                new Restaurant("r3", "a3", RestaurantType.TURKISH)
        );
    }

    private static List<Meal> aMealList() {
        return List.of(
                new Meal("m1", 100.0, new Restaurant(null, null, null)),
                new Meal("m2", 200.0, new Restaurant(null, null, null)),
                new Meal("m3", 300.0, new Restaurant(null, null, null))
        );
    }

    @Test
    void should_map_create_meal_dto_to_meal() {
        // given
        var createMealDto = new CreateMealDto("exampleMeal", 100.0);

        // when
        var result = map(createMealDto);

        // then
        assertThat(result.getName()).isEqualTo(createMealDto.getName());
    }

    @Test
    void should_map_create_restaurant_dto_to_restaurant() {
        // given
        final var createRestaurantDto = new CreateRestaurantDto("exampleRestaurant", "warsaw", RestaurantType.ITALIAN);

        // when
        final var result = map(createRestaurantDto);

        // then
        assertThat(result.getClass()).isEqualTo(Restaurant.class);
    }

    @Test
    void should_map_restaurant_to_restaurant_dto() {
        // given
        final var restaurant = new Restaurant("exampleRestaurant", "warsaw", RestaurantType.ITALIAN);

        // when
        final var result = map(restaurant);

        // then
        assertThat(result.getClass()).isEqualTo(RestaurantDto.class);
    }

    @Test
    void should_map_meal_to_meal_dto() {
        // given
        final var meal = new Meal("example", 100.0);
        final var restaurant = mock(Restaurant.class);
        meal.setRestaurant(restaurant);

        // when
        final var result = map(meal);

        // then
        assertThat(result.getRestaurantName()).isEqualTo(meal.getRestaurant().getName());
        assertThat(result.getName()).isEqualTo(meal.getName());
        assertThat(result.getPrice()).isEqualTo(meal.getPrice());
        assertThat(result.getId()).isEqualTo(meal.getId());
    }

    @Test
    void should_map_restaurant_list_to_restaurant_dto() {
        // given
        final var listRestaurant = aRestauratantList();

        // when
        final var mapListRestaurant = map(listRestaurant);

        // then
        mapListRestaurant
                .forEach(r -> assertThat(r.getClass()).isEqualTo(RestaurantDto.class));
    }

    @Test
    void should_map_meal_list_to_meal_dto() {
        // given
        final var mealList = aMealList();
        // when
        final var mapMealList = mapListMealsToDto(mealList);
        // then
        mapMealList
                .forEach(m -> assertThat(m.getClass()).isEqualTo(MealDto.class));
    }

    @Test
    void should_throw_runtime_exception_when_parameter_null() {
        // given
        CreateMealDto createMealDto = null;

        // then
        assertThatThrownBy(() -> map(createMealDto)).isInstanceOf(RuntimeException.class);
    }


}
