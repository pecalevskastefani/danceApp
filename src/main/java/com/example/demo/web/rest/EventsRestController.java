package com.example.demo.web.rest;

import com.example.demo.model.VideoReleaseDate;
import com.example.demo.service.UserService;
import com.example.demo.service.VideoReleaseDateService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

@RestController
public class EventsRestController {


    private final VideoReleaseDateService videoReleaseDateService;
    private final UserService userService;

    public EventsRestController( VideoReleaseDateService videoReleaseDateService, UserService userService) {
        this.videoReleaseDateService = videoReleaseDateService;
        this.userService = userService;
    }


    @RequestMapping("/events")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("bodyContent","calendar");
        modelAndView.setViewName("master-template");
        modelAndView.getModel().put("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0]);

        return modelAndView;

    }

    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<VideoReleaseDate> events(@RequestParam("start")
                           @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
                                      @RequestParam("end")
                           @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return videoReleaseDateService.findBetween(start, end);
    }

    @PostMapping("/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    VideoReleaseDate createEvent(@RequestBody EventCreateParams params) {
        return videoReleaseDateService.create(params.start,params.end, params.text);
    }

    @PostMapping("/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    VideoReleaseDate moveEvent(@RequestBody EventMoveParams params) {
        return videoReleaseDateService.changeDate(params.id,params.start,params.end);
    }

    @PostMapping("/api/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    VideoReleaseDate setColor(@RequestBody SetColorParams params) {
        return videoReleaseDateService.putColor(params.id,params.color);
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;
        public Long resource;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
        public Long resource;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }


}