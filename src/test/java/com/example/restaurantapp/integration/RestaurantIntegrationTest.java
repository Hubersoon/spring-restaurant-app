package com.example.restaurantapp.integration;

import com.example.restaurantapp.RestaurantAppApplication;
import com.example.restaurantapp.TestUtils;
import com.example.restaurantapp.reposiotry.RestaurantRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RestaurantAppApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RestaurantIntegrationTest {

    private static final String CONTEXT_RESTAURANTS = "restaurants";
    private static final String CONTEXT_MEALS = "meals";
    private static final String BASE_URL = "/api/restaurants";

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
    public void should_get_list_of_all_restaurants() throws FileNotFoundException {
        given()
                .when().get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("", equalTo(TestUtils.getPath("response/get-all-restaurant-response.json", CONTEXT_RESTAURANTS).get()));
    }

    @Test
    public void should_get_list_of_all_meals() throws FileNotFoundException {
        given()
                .when().get(BASE_URL + "/meals")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("", equalTo(TestUtils.getPath("response/get_all_meals.json", CONTEXT_MEALS).get()));
    }

    @Test
    public void should_get_restaurant_meal_list() throws FileNotFoundException {
        final var restaurantId = "/81391996-3172-11eb-adc1-0242ac120002";
        given()
                .when().get(BASE_URL + restaurantId + "/meals")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("", equalTo(TestUtils.getPath("response/get_restaurant_meal_list.json", CONTEXT_RESTAURANTS).get()));
    }

    @Test
    public void should_add_restaurant() throws IOException {
        given().contentType(ContentType.JSON).body(TestUtils.getRequestBodyFromFile("request/add_restaurant_request.json", CONTEXT_RESTAURANTS))
                .when().post(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and().body("name", is("Fat Burger"))
                .and().body("address", is("Paris"))
                .and().body("type", is("AMERICAN"));
    }

    @Test
    public void should_add_meal_to_restaurant() throws IOException {
        final var restaurantId = "/81391996-3172-11eb-adc1-0242ac120002";
        given().contentType(ContentType.JSON).body(TestUtils.getRequestBodyFromFile("request/add_meal_request.json", CONTEXT_MEALS))
                .when().body(BASE_URL + restaurantId + "/meals")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and().body("restaurantName", is("Sushi Osaka"))
                .and().body("name", is("Sushi Nigiri"))
                .and().body("price", is(45));
    }

    @Test
    public void should_update_restaurant_data() throws IOException {
        final var restaurantId = "/81391996-3172-11eb-adc1-0242ac120002";
        given().contentType(ContentType.JSON).body(TestUtils.getRequestBodyFromFile("request/update_restaurant_data_request.json", CONTEXT_RESTAURANTS))
                .when().patch(BASE_URL + restaurantId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("", equalTo(TestUtils.getPath("response/update_restaurant_data_response.json", CONTEXT_RESTAURANTS).get()));
    }

    @Test
    public void should_delete_meals_from_restaurant() {
        final var restaurantId = "/81391996-3172-11eb-adc1-0242ac120002";
        given()
                .when().delete(BASE_URL + restaurantId + "/meals")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void should_delete_restaurant() {
        final var restaurantId = "81391996-3172-11eb-adc1-0242ac120002";
        given()
                .when().delete(BASE_URL + "/" + restaurantId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        // and
        Assertions.assertThat(restaurantRepository.findById(UUID.fromString(restaurantId))).isEmpty();

    }

    @Test
    public void should_delete_all_restaurants() {
        given()
                .when().delete(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}