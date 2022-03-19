package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    @ManyToOne
    Category category;
    @ManyToOne
    Instructor instructor;
    String url;

    public Video() {
    }

    public Video(String title, String description, Category category, Instructor instructor,String url) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.instructor= instructor;
        this.url=url;
    }

}
