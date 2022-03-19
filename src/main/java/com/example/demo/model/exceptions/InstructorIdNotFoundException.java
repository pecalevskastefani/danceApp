package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InstructorIdNotFoundException extends RuntimeException{
    public InstructorIdNotFoundException(Long id) {

        super(String.format("Instructor with id: %d is not found", id));
    }
}
