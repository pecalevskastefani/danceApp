package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.exceptions.CategoryIdNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategroyServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategroyServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        return this.categoryRepository.save(new Category(name,description));
    }

    @Override
    public Category update(Long id, String name, String description) {
        Category cat = this.categoryRepository.findById(id).orElseThrow(()->new CategoryIdNotFoundException(id));
        cat.setName(name);
        cat.setDescription(description);
        return this.categoryRepository.save(cat);
    }

    @Override
    public void delete(String name) {
        this.categoryRepository.deleteByName(name);
    }

    @Override
    public List<Category> listAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(()->new CategoryIdNotFoundException(id));
    }
}
