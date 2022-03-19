package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(Long id) {

        super(String.format("Skill with id: %d is not found", id));
    }
}