package com.example.demo.service.impl;

import com.example.demo.model.Program;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.ProgramIdNotFoundException;
import com.example.demo.repository.ProgramsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgramsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramsServiceImpl implements ProgramsService{
    private final ProgramsRepository programsRepository;
    private final UserRepository userRepository;

    public ProgramsServiceImpl(ProgramsRepository programsRepository, UserRepository userRepository) {
        this.programsRepository = programsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Program findById(Long id) {
        return this.programsRepository.findById(id).orElseThrow(()->new ProgramIdNotFoundException(id));
    }

    @Override
    public List<Program> findAll() {
        return this.programsRepository.findAll();
    }

    @Override
    public Program save(String name, String description, Double price, String url,List<User> users) {
        return this.programsRepository.save(
                new Program(name, description, price, url,users)
        );
    }

    @Override
    public void deleteById(Long id) {
        this.programsRepository.deleteById(id);
    }

    @Override
    public Program update(Long id, String name, String description, Double price,List<User> users) {
        Program program = this.findById(id);
        program.setName(name);
        program.setDescription(description);
        program.setPrice(price);
        program.setUsers(users);
        return this.programsRepository.save(program);
    }

    @Override
    public void addUserToProgram(Long programId,String username) {
        User user = this.userRepository.findByUsername(username);
        Program program = this.findById(programId);
        program.getUsers().add(user);

    }
}
