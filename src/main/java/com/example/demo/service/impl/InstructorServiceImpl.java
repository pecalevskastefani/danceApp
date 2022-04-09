package com.example.demo.service.impl;

import com.example.demo.model.Favorites;
import com.example.demo.model.Instructor;
import com.example.demo.model.Skills;
import com.example.demo.model.Video;
import com.example.demo.model.exceptions.InstructorIdNotFoundException;
import com.example.demo.model.exceptions.SkillNotFoundException;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.SkillsRepository;
import com.example.demo.repository.VideoRepository;
import com.example.demo.service.InstructorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final SkillsRepository skillsRepository;
    private final VideoRepository videoRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, SkillsRepository skillsRepository, VideoRepository videoRepository) {
        this.instructorRepository = instructorRepository;
        this.skillsRepository = skillsRepository;
        this.videoRepository = videoRepository;
    }


    @Override
    public Instructor findById(Long id) {
        return this.instructorRepository.findById(id).orElseThrow(()->new InstructorIdNotFoundException(id));
    }

    @Override
    public List<Instructor> findAll() {
        return this.instructorRepository.findAll();
    }

    @Override
    public void save(String name, String surname, String url) {
        Instructor instructor = new Instructor(name,surname,null,url);
         this.instructorRepository.save(instructor);

    }

    @Override
    public void deleteById(Long id) {
        Collection<Video> videos = this.videoRepository.findAll();
        for(Video v :videos){
            if(v.getInstructor() != null && v.getInstructor().getId().equals(id)){
                v.setInstructor(null);
            }
        }
        this.instructorRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String name, String surname, List<Skills> skills, String url) {
        Instructor instructor = this.findById(id);
        instructor.setName(name);
        instructor.setSurname(surname);
        instructor.setSkills(skills);
        instructor.setUrl(url);
        this.assignSkill(id,skills);
    }

    @Override
    public Optional<Instructor> assignSkill(Long instructorId, List<Skills> skills) {
       Instructor instructor = this.findById(instructorId);
       instructor.setSkills(skills);
       return  Optional.of(this.instructorRepository.save(instructor));
    }

    @Override
    public Instructor findByName(String name) {
        return this.instructorRepository.findByName(name);
    }


}
