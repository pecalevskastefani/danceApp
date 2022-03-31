package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    Double price;
    String url;
   // @OneToMany(mappedBy = "program",cascade = CascadeType.ALL)
    //List<User> users = new ArrayList<>();

    public Program(){

    }
    public Program(String name, String description, Double price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
       // this.users=new ArrayList<>();
    }

}