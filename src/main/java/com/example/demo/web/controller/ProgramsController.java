package com.example.demo.web.controller;

import com.example.demo.model.*;
import com.example.demo.service.ProgramsService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProgramsController {
    private final ProgramsService programsService;
    private final UserService userService;

    public ProgramsController(ProgramsService programsService, UserService userService) {
        this.programsService = programsService;
        this.userService = userService;
    }

    @GetMapping("/programs")
    public String getProgramsPage (@RequestParam(required = false) String error,
                                   HttpServletRequest req,
                                   Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("programs", this.programsService.findAll());
        model.addAttribute("bodyContent", "programs");
        return "master-template";
    }


    @PostMapping("/deleteProgram/{id}")
    public String deleteProgram(@PathVariable Long id) {
        this.programsService.deleteById(id);
        return "redirect:/programs";
    }

    @GetMapping("/programs/add-form")
    public String addProgramPage(Model model) {
        List<Program> programs = this.programsService.findAll();
        model.addAttribute("programs", programs);
        model.addAttribute("bodyContent", "add-program");
        return "master-template";

    }

    @GetMapping("/programs/edit-form/{id}")
    public String editProgramPage(@PathVariable Long id, Model model) {
        if (this.programsService.findById(id) != null) {
            Program program = this.programsService.findById(id);
            model.addAttribute("program", program);
            model.addAttribute("bodyContent", "add-program");
            return "master-template";
        }
        return "redirect:/videos?error=VideoNotFound"; //?????????
    }

    @PostMapping("/programs/add")
    public String saveProgram(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String url) {
        //@RequestParam(required = false) List<User> users
        if (id != null) {
            this.programsService.update(id,name,description,price);
        } else {
        //    List<User> emptyUsers = new ArrayList<>();
            this.programsService.save(name,description,price,url);
        }
        return "redirect:/programs";
    }

    @GetMapping("/programs/apply/{id}")
    public String applyToProgram(HttpServletRequest request,
                                 @PathVariable Long id) {
        String username = request.getRemoteUser();
        this.userService.addUserToProgram(id,username);
        return "redirect:/programs";
    }
    @GetMapping("/programs/usersInProgram/{id}")
    public String applyToProgram(Model model,
                                 @PathVariable Long id) {
       model.addAttribute("program",this.programsService.findById(id));
       model.addAttribute("users",this.programsService.listUsersInProgram(id));
        return "users-in-program";
    }
}