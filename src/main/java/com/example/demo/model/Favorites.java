package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    User user;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Video> videos = new ArrayList<>();

    public Favorites() {
    }

    public Favorites(User user, List<Video> videos) {
        this.user = user;
        this.videos = new ArrayList<Video>();
    }

    public Favorites(User user) {
        this.user = user;
    }
}
