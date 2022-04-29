package com.example.demo.service.impl;

import com.example.demo.model.VideoReleaseDate;
import com.example.demo.repository.VideoReleaseDateRepository;
import com.example.demo.service.VideoReleaseDateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class VideoReleaseDateImpl implements VideoReleaseDateService {
    private final VideoReleaseDateRepository videoReleaseDateRepository;

    public VideoReleaseDateImpl(VideoReleaseDateRepository videoReleaseDateRepository) {
        this.videoReleaseDateRepository = videoReleaseDateRepository;
    }

    @Override
    public VideoReleaseDate create(LocalDateTime start, LocalDateTime end, String text) {
        return videoReleaseDateRepository.save(new VideoReleaseDate(start,end,text));
    }

    @Override
    public VideoReleaseDate changeDate(Long id, LocalDateTime start, LocalDateTime end) {
        VideoReleaseDate video = videoReleaseDateRepository.findById(id).get();
        video.setStart(start);
        video.setEnd(end);
        return videoReleaseDateRepository.save(video);
    }

    @Override
    public VideoReleaseDate putColor(Long id, String color) {
        VideoReleaseDate video = videoReleaseDateRepository.findById(id).get();
        video.setColor(color);
        return videoReleaseDateRepository.save(video);
    }

    @Override
    public List<VideoReleaseDate> findBetween(LocalDateTime start, LocalDateTime end) {
        return videoReleaseDateRepository.findBetween(start,end);
    }
}
