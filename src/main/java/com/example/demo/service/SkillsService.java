package com.example.demo.service;

import com.example.demo.model.Skills;

import java.util.List;

public interface SkillsService {
    Skills create(String name);
    List<Skills> findAll();
}
