package com.example.restaurantapp.service;

import com.example.restaurantapp.exceptions.NotFoundMealException;
import com.example.restaurantapp.exceptions.NotFoundResturantException;
import com.example.restaurantapp.model.Meal;
import com.example.restaurantapp.model.Restaurant;
import com.example.restaurantapp.model.RestaurantType;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import com.example.restaurantapp.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ServiceTest
{

    private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
    private final RestaurantService restaurantService = new RestaurantService(restaurantRepository);

    @Test
    void should_add_restaurant() {
        // given
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));
        // when
        var result = restaurantService.addRestaurant(restaurant);
        // then
//        assertTrue(result);
        assertThat(restaurantRepository.getRestaurantList()).contains(restaurant);
    }

    @Test
    void should_not_add_restaurant_when_null() {
        // given
        Restaurant restaurant = null;
        // when
        var result = restaurantService.addRestaurant(restaurant);
        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_add_meal_to_restaurant() {
        // given
        var meal = new Meal("beef burger", 28.9);
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));

        // when
        var result = restaurantService.addMeal(meal, restaurant.getId());

        // then
        assertTrue(result);
        assertThat(restaurant.getMealsList()).contains(meal);
    }

    @Test
    void should_not_add_meal_if_null() {
        // given
        Meal meal = null;
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));
        // when
        var result = restaurantService.addMeal(meal, restaurant.getId());
        // then
        assertFalse(result);
        assertThat(restaurant.getMealsList()).doesNotContain(meal);
    }

    @Test
    void should_throw_exception_when_restaurant_not_exist() {
        // given
        UUID randomRestaurantId = UUID.randomUUID();
        // then
        final var exception = assertThrows(NotFoundResturantException.class, () -> restaurantService.getRestaurantMeals(randomRestaurantId));
        assertThat(exception.getMessage()).contains("Restaurant doesn't exist in the app");

    }
    @Test
    void should_throw_exception_when_meal_not_exist() {
        // given
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        final var restaurantId = restaurant.getId();
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));
        restaurantService.addRestaurant(restaurant);
        UUID randomMealId = UUID.randomUUID();
        // then
        final var exception = assertThrows(NotFoundMealException.class, () -> restaurantService.deleteMeal(restaurantId, randomMealId));
        assertThat(exception.getMessage()).contains("Meal doesn't exist");
    }

    @Test
    void should_get_restaurant_meals_list() {
        // given
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        var meal1 = new Meal("beef burger", 28.9);
        var meal2 = new Meal("chicken burger", 28.9);
        List<Meal> mealsList = List.of(meal1, meal2);
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));
        restaurantService.addRestaurant(restaurant);

        // when
        final var result1 = restaurantService.addMeal(meal1, restaurant.getId());
        final var result2 = restaurantService.addMeal(meal2, restaurant.getId());

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(restaurant.getMealsList()).isEqualTo(mealsList);
    }
    @Test
    void should_delete_restaurant_from_repository() {
        // given
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));
        when(restaurantRepository.deleteRestaurant(restaurant)).thenReturn(true);
        // when
        final var b = restaurantService.deleteRestaurant(restaurant.getId());
        // then
        assertThat(restaurantRepository.getRestaurantList()).doesNotContain(restaurant);
    }
    @Test
    void should_delete_meal_from_restaurant() {
        // given
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        var meal1 = new Meal("beef burger", 28.9);
        var meal2 = new Meal("chicken burger", 25.0);
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(restaurant));
        restaurantService.addMeal(meal1, restaurant.getId());
        restaurantService.addMeal(meal2, restaurant.getId());

        // when
        final var result = restaurantService.deleteMeal(restaurant.getId(), meal1.getId());

        // then
        assertTrue(result);
        assertThat(restaurant.getMealsList()).doesNotContain(meal1);
        assertThat(restaurant.getMealsList()).contains(meal2);
    }
    @Test
    void should_change_restaurant_name() {
//        given
        var restaurant = new Restaurant("Burger's", "kielce", RestaurantType.AMERICAN);
        String newRestaurantName = "Pizza&Pasta";
        final var e1 = restaurantService.changeRestaurantName(newRestaurantName, restaurant.getId());
        when(restaurantRepository.getRestaurantList()).thenReturn(List.of(e1));
//        when
//        assertThat(restaurant.getName()).isNotEqualTo(newRestaurantName);
//        final var updatedRestaurant = restaurantService.changeRestaurantName(newRestaurantName, restaurant.getId());
//        then
//        assertThat(restaurant.getName()).isEqualTo(newRestaurantName);
//        assertTrue(restaurantRepository.getRestaurantList().contains(updatedRestaurant));
//        Assertions.assertFalse(restaurantRepository.getRestaurantList().contains(restaurant));
    }
//    @Test
//    void should_change_restaurant_address() {
////        given
//        var restaurant = new CreateRestaurantDTO("Burger's", "kielce", RestaurantType.AMERICAN);
//        final var restaurantId = restaurant.getId();
//        restaurantService.addRestaurant(restaurant);
//        String newRestaurantAddress = "Zakopane";
////        when
//        Assertions.assertNotEquals(newRestaurantAddress,restaurant.getAddress());
//        final var updatedRestaurant = restaurantService.changeRestaurantAddress(newRestaurantAddress, restaurantId);
////        then
//        Assertions.assertEquals(newRestaurantAddress,updatedRestaurant.getAddress());
//        assertTrue(restaurantRepository.getRestaurantList().contains(updatedRestaurant));
//        Assertions.assertFalse(restaurantRepository.getRestaurantList().contains(restaurant));
//    }
//    @Test
//    void should_change_restaurant_type() {
////        given
//        var restaurant = new CreateRestaurantDTO("Burger's", "kielce", RestaurantType.AMERICAN);
//        final var restaurantId = restaurant.getId();
//        restaurantService.addRestaurant(restaurant);
//        RestaurantType newRestaurantType = RestaurantType.ASIAN;
////        when
//        Assertions.assertNotEquals(newRestaurantType,restaurant.getType());
//        final var updatedRestaurant = restaurantService.changeRestaurantType(newRestaurantType, restaurantId);
////        then
//        Assertions.assertEquals(newRestaurantType,updatedRestaurant.getType());
//        assertTrue(restaurantRepository.getRestaurantList().contains(updatedRestaurant));
//        Assertions.assertFalse(restaurantRepository.getRestaurantList().contains(restaurant));
//    }


}

