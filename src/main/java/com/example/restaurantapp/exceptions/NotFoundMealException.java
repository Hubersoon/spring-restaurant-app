package com.example.restaurantapp.exceptions;

public class NotFoundMealException extends RuntimeException {

    public NotFoundMealException(String message) {
        super(message);
    }
}
