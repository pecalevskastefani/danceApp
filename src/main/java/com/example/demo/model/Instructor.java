package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;

    @Column(length=1000000)
    String url;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Skills> skills ;

    public Instructor() {
    }

    public Instructor(String name, String surname, List<Skills> skills, String url) {
        this.name = name;
        this.surname = surname;
        this.url = url;
        this.skills = new ArrayList<Skills>();
    }
    public Instructor(String name) {
        this.name=name;
    }
}
