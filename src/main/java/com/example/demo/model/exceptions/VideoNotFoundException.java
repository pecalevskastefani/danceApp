package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VideoNotFoundException extends RuntimeException{
    public VideoNotFoundException(Long id) {
        super(String.format("Video with id: %d is not found", id));
    }
}

