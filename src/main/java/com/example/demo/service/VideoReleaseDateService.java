package com.example.demo.service;

import com.example.demo.model.VideoReleaseDate;

import java.time.LocalDateTime;
import java.util.List;

public interface VideoReleaseDateService {
    VideoReleaseDate create(LocalDateTime start, LocalDateTime end, String text);
    VideoReleaseDate changeDate(Long id,LocalDateTime start,LocalDateTime end);
    VideoReleaseDate putColor(Long id,String color);
    List<VideoReleaseDate> findBetween(LocalDateTime start, LocalDateTime end);
}
