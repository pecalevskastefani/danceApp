package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoritesNotFoundException extends RuntimeException{

    public FavoritesNotFoundException(Long id) {
        super(String.format("Favorites with id: %d was not found", id));
    }
}
