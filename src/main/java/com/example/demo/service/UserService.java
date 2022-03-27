package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {
    User register(String email, String password, String repeatPassword, String name, String surname, Role role, LocalDate birthday);
    User create(String email,String password, String name,String surname, Role role, LocalDate birthday);
    List<User> findAll();
    User findByUsername(String email);
}
