package com.example.demo.web.controller;

import com.example.demo.model.Program;
import com.example.demo.model.Role;
import com.example.demo.model.exceptions.InvalidArgumentsException;
import com.example.demo.model.exceptions.PasswordsDoNotMatchException;
import com.example.demo.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage( Model model){
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam Role role,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday){
        try{
            this.userService.register(email,password,repeatedPassword,name,surname,role, birthday);
            return "redirect:/login"; //otkako ke se registrira neka se najavi
        }
        catch (PasswordsDoNotMatchException | InvalidArgumentsException exception) {
            return "redirect:/register?error="+ exception.getMessage();
        }
    }
}
