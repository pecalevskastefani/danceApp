package com.example.demo.service;

import com.example.demo.model.Instructor;
import com.example.demo.model.Skills;

import java.util.List;
import java.util.Optional;

public interface InstructorService {
    Instructor findById(Long id);
    List<Instructor> findAll();
    void save(String name, String surname, String url);
    void deleteById(Long id);
    void update(Long id, String name, String surname, List<Skills> skills, String url);
    Optional<Instructor> assignSkill(Long instructorId, List<Skills> skills);

}
