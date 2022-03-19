package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmailExistsException extends RuntimeException{
    public EmailExistsException() {
        super(String.format("There is a user with this email"));
    }
}
