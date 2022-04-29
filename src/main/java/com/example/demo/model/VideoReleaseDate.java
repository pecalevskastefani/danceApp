package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class VideoReleaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDateTime start;

    @Column(name = "endTime")
    private LocalDateTime end;

    private String color;

    public VideoReleaseDate(){}

    public VideoReleaseDate(LocalDateTime start, LocalDateTime end, String text){
        this.start = start;
        this.end = end;
        this.text = text;
    }
}

