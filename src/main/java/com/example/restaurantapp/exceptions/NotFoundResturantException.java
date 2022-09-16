package com.example.restaurantapp.exceptions;

public class NotFoundResturantException extends RuntimeException {

    public NotFoundResturantException(String message) {
        super(message);
    }
}
