package com.example.demo.web.rest;

import com.example.demo.model.VideoReleaseDate;
import com.example.demo.model.staticClasses.EventCreateParams;
import com.example.demo.model.staticClasses.EventMoveParams;
import com.example.demo.model.staticClasses.SetColorParams;
import com.example.demo.service.UserService;
import com.example.demo.service.VideoReleaseDateService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Role;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

@RestController
public class ReleaseDateController {


    private final VideoReleaseDateService videoReleaseDateService;
    private final UserService userService;

    public ReleaseDateController(VideoReleaseDateService videoReleaseDateService, UserService userService) {
        this.videoReleaseDateService = videoReleaseDateService;
        this.userService = userService;
    }


    @RequestMapping("/releaseDates")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("bodyContent","calendar");
        modelAndView.setViewName("master-template");
        modelAndView.getModel().put("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0]);

        return modelAndView;

    }

    @GetMapping("/api/dates")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<VideoReleaseDate> events(@RequestParam("start")
                           @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
                                      @RequestParam("end")
                           @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return videoReleaseDateService.findBetween(start, end);
    }

    @PostMapping("/api/dates/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    VideoReleaseDate createEvent(@RequestBody EventCreateParams params) {
        return videoReleaseDateService.create(params.start,params.end, params.text);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/dates/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    VideoReleaseDate moveEvent(@RequestBody EventMoveParams params) {
        return videoReleaseDateService.changeDate(params.id,params.start,params.end);
    }

    @PostMapping("/api/dates/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    VideoReleaseDate setColor(@RequestBody SetColorParams params) {
        return videoReleaseDateService.putColor(params.id,params.color);
    }

}
