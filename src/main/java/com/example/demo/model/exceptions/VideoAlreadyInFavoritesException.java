package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class VideoAlreadyInFavoritesException extends RuntimeException {
    public VideoAlreadyInFavoritesException(Long id, String username) {
        super(String.format("Product with id: %d already exists in favorites for user with email %s", id, username));
    }

}



