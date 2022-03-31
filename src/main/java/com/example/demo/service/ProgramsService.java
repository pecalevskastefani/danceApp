package com.example.demo.service;


import com.example.demo.model.*;

import java.util.List;


public interface ProgramsService {
    Program findById(Long id);
    List<Program> findAll();
    Program save(String name, String description, Double price, String url);
    void deleteById(Long id);
    Program update(Long id, String name, String description, Double price);
  //  void addUserToProgram(Long programId,String username);
    List<User> listUsersInProgram(Long programId);
}