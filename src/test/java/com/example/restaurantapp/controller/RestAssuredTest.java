package com.example.restaurantapp.controller;

import com.example.restaurantapp.RestaurantAppApplication;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RestaurantAppApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAssuredTest {

    private final static String baseURL = "/api/restaurants/";
    private final static String requestBody = "" +
            "{\"name\":\"assuredRestaurant\"" +
            ",\"address\":\"warsaw\"" +
            ",\"type\":\"AMERICAN\"}";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void should_get_restaurant_list() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURL)
                .then()
                .extract().response();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("name[0]")).isEqualTo("Pizza");
        assertThat(response.jsonPath().getString("name[1]")).isEqualTo("Burger's");

    }

    @Test
    public void should_post_restaurant() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURL)
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().asString()).isEqualTo("true");
    }

    @Test
    public void deleteRestaurant() {
        var restaurantId = UUID.fromString("935237ca-01ff-4e32-a212-86853bb48462");
        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(baseURL + restaurantId)
                .then()
                .extract().response();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().asString()).isEqualTo("true");
        restaurantRepository.getRestaurantList()
                .forEach(restaurant -> assertThat(restaurant.getId().toString()).isNotEqualTo(restaurantId));
    }

    @Test
    public void should_patch_restaurant_data() {
        var restaurantId = UUID.fromString("935237ca-01ff-4e32-a212-86853bb48462");
        final var response = given()
                .contentType(ContentType.JSON)
                .queryParam("name", "newName")
                .queryParam("address", "newAddress")
                .when()
                .patch(baseURL + restaurantId)
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(200);
        restaurantRepository.restaurantList.stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .forEach(restaurant -> {
                    assertThat(restaurant.getName()).isEqualTo("newName");
                    assertThat(restaurant.getAddress()).isEqualTo("newAddress");
                });
    }


}
