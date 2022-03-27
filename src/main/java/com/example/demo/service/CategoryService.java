package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(String name, String description);
    Category update(Long id, String name, String description);
    void delete(String name);

    List<Category> listAll();
    Category findById(Long id);
}
