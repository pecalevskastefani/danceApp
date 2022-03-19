package com.example.demo.service;


import com.example.demo.model.*;

import java.util.List;


public interface ProgramsService {
    Program findById(Long id);
    List<Program> findAll();
    Program save(String name, String description, Double price, String url, List<User> users);
    void deleteById(Long id);
    Program update(Long id, String name, String description, Double price,List<User> users);
   void addUserToProgram(Long programId,String username);
}