package com.example.demo.service.impl;

import com.example.demo.model.Skills;
import com.example.demo.repository.SkillsRepository;
import com.example.demo.service.SkillsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillsServiceImpl implements SkillsService {
    private final SkillsRepository skillsRepository;

    public SkillsServiceImpl(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @Override
    public Skills create(String name) {
        return this.skillsRepository.save(new Skills(name));
    }

    @Override
    public List<Skills> findAll() {
        return this.skillsRepository.findAll();
    }
}
