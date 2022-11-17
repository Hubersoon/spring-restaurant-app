package com.example.restaurantapp.service;


import com.example.restaurantapp.exceptions.NotFoundMealException;
import com.example.restaurantapp.exceptions.NotFoundResturantException;
import com.example.restaurantapp.exceptions.NullDtoException;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import com.example.restaurantapp.model.UpdateRestaurantRequest;
import com.example.restaurantapp.model.dto.CreateMealDto;
import com.example.restaurantapp.model.dto.CreateRestaurantDto;
import com.example.restaurantapp.reposiotry.MealReposiotry;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.restaurantapp.controller.mapper.Mapper.map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
    private final MealReposiotry mealReposiotry = mock(MealReposiotry.class);
    private final RestaurantService restaurantService = new RestaurantService(restaurantRepository, mealReposiotry);

    @Test
    void should_add_restaurant() {
        // given
        final var restaurantDto = new CreateRestaurantDto("Burger's", "kielce", RestaurantType.AMERICAN);
        final var restaurant = map(restaurantDto);
        given(restaurantRepository.save(restaurant)).willReturn(restaurant);

        // when
        final var result = restaurantService.addRestaurant(restaurantDto);

        // then
        assertThat(result.getAddress()).isEqualTo(restaurant.getAddress());
        assertThat(result.getName()).isEqualTo(restaurant.getName());
        assertThat(result.getId()).isNotNull();
        then(restaurantRepository).should().save(restaurant);
//        then(restaurantRepository).shouldHaveNoMoreInteractions();
    }


    @Test
    void should_not_add_restaurant_when_null() {
        // given
        CreateRestaurantDto restaurant = null;

        // then
        final var exception = assertThrows(NullDtoException.class, () -> restaurantService.addRestaurant(restaurant));
        assertThat(exception.getMessage()).contains("Object cannot be null to map");
        then(restaurantRepository).should(never()).save(any());
        then(restaurantRepository).shouldHaveNoInteractions();
    }


    @Test
    void should_add_meal_to_restaurant() {
        // given
        final var mealDto = new CreateMealDto("beef burger", 28.9);
        final var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        // when
        var result = restaurantService.addMeal(restaurant.getId(), mealDto);

        // then
        assertThat(result).isEqualTo(map(restaurant.getMealsList().get(0)));
        assertThat(result.getRestaurantName()).isEqualTo(restaurant.getName());
    }

    @Test
    void should_not_add_meal_if_null() {
        // given
        CreateMealDto mealDto = null;
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        // then
        final var exception = assertThrows(NullDtoException.class, () -> restaurantService.addMeal(restaurant.getId(), mealDto));
        assertThat(exception.getMessage()).isEqualTo("Input object cannot be null");
        then(mealReposiotry).should(never()).save(any());
    }

    @Test
    void should_throw_exception_when_restaurant_not_exist() {
        // given
        final var randomId = UUID.randomUUID();

        // then
        final var exception = assertThrows(NotFoundResturantException.class, () -> restaurantService.getRestaurant(randomId));
        assertThat(exception.getMessage()).contains("Restaurant not found");
    }

    @Test
    void should_throw_exception_when_meal_not_exist() {
        // given
        final var randomId = UUID.randomUUID();

        // then
        final var exception = assertThrows(NotFoundMealException.class, () -> restaurantService.deleteMeal(randomId));
        assertThat(exception.getMessage()).contains("Meal not found");
    }

    @Test
    void should_get_restaurant_meals() {
        // given
        final var restaurant1 = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        final var restaurant2 = new Restaurant("Pizza", "kielce", RestaurantType.AMERICAN);
        final var meal1 = new Meal("beef burger", 28.9, restaurant1);
        final var meal2 = new Meal("pizza", 33.9, restaurant1);
        final var meal3 = new Meal("sushi", 40.0, restaurant2);
        when(restaurantRepository.findById(restaurant1.getId())).thenReturn(Optional.of(restaurant1));
        when(mealReposiotry.findAll()).thenReturn(List.of(meal1, meal2, meal3));

        // when
        var result1 = restaurantService.getMealsFromRestaurant(restaurant1.getId());

        // then
        assertThat(result1).contains(map(meal1), map(meal2));

    }

    @Test
    void should_update_restaurant_name_and_address() {
        // given
        final var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        final var newName = Optional.of("Pizza Albatros");
        final var newAddress = Optional.of("New York");
        final var updateRequest = new UpdateRestaurantRequest(newName,newAddress,Optional.empty(),Optional.empty());
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRequest);

        // then
        assertThat(result.getName()).isEqualTo(map(restaurant).getName());
        assertThat(result.getAddress()).isEqualTo(map(restaurant).getAddress());
    }

}

