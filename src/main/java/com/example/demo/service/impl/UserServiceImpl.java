package com.example.demo.service.impl;

import com.example.demo.model.Program;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.InvalidArgumentsException;
import com.example.demo.model.exceptions.PasswordsDoNotMatchException;
import com.example.demo.repository.ProgramsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProgramsRepository programsRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ProgramsRepository programsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.programsRepository = programsRepository;
    }

    @Override
    public User register(String email, String password, String repeatPassword, String name, String surname, Role role, LocalDate birthday) {
        if (email==null || email.isEmpty()|| password==null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        if(!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }
        User user = new User(name,surname,email,passwordEncoder.encode(password),role, birthday,null);
        return userRepository.save(user);
    }

    @Override
    public User create(String email, String password, String name, String surname, Role role, LocalDate birthday) {
        User user = new User(name,surname,email,this.passwordEncoder.encode(password),role, birthday,null);
        return this.userRepository.save(user);
    }


    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByUsername(String email) {
        return this.userRepository.findByUsername(email);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.findByUsername(s);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList()));
    }
    @Override
    public void addUserToProgram(Long programId,String username) {
        User user = this.findByUsername(username);
        Optional<Program> program = this.programsRepository.findById(programId);
        program.get().setStart(LocalDateTime.now());
        if(program.get().getName().equals("Monthly Plan")){
            program.get().setEnd(LocalDateTime.now().plusMonths(1));
        }
        else if(program.get().getName().equals("3 Month Plan")){
            program.get().setEnd(LocalDateTime.now().plusMonths(3));
        }
        else if(program.get().getName().equals("Annual Plan")){
            program.get().setEnd(LocalDateTime.now().plusMonths(12));
        }
        user.setProgram(program.get());

        this.userRepository.save(user);
    }

    @Override
    public void cancelProgram( User username) {
        Program program = null;
        username.setProgram(program);
        this.userRepository.save(username);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

}
