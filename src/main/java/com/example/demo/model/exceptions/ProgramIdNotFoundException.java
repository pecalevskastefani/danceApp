package com.example.demo.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProgramIdNotFoundException extends RuntimeException{
    public ProgramIdNotFoundException(Long id) {
        super(String.format("Program with id: %d is not found", id));
    }
}
