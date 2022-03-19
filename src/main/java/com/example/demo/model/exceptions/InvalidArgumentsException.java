package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidArgumentsException extends RuntimeException{
    public InvalidArgumentsException(){
        super("Ivalid arguments exception!!");
    }
}
