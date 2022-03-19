package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Instructor;
import com.example.demo.model.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findById(Long id);
    void deleteById(Long id);
    Video create(String title, String description, Category category, Instructor coach,String url);
    Video update(Long id, String title, String description, Long category,Long coach,String url);
    List<Video> filter(String title, Long categoryId);
}
