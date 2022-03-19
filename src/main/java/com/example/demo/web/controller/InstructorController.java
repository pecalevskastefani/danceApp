package com.example.demo.web.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Instructor;
import com.example.demo.model.Program;
import com.example.demo.model.Skills;
import com.example.demo.service.InstructorService;
import com.example.demo.service.SkillsService;
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
public class InstructorController {
    private final InstructorService instructorService;
    private final SkillsService skillsService;
    public InstructorController(InstructorService instructorService, SkillsService skillsService) {
        this.instructorService = instructorService;
        this.skillsService = skillsService;
    }

    @GetMapping("/instructors")
    public String getInstructorsPage(@RequestParam(required = false) String error,
                                   HttpServletRequest req,
                                   Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("skills",this.skillsService.findAll());
        model.addAttribute("instructors", this.instructorService.findAll());
        List<Instructor> instructors = this.instructorService.findAll();
        model.addAttribute("bodyContent", "instructors");
        return "master-template";
    }


    @GetMapping("/instructors/add-form")
    public String addInstructorPage(Model model) {
        List<Skills> skills = this.skillsService.findAll();
        List<Instructor> instructors = this.instructorService.findAll();
        model.addAttribute("instructors", instructors);
        model.addAttribute("skills", skills);
        model.addAttribute("bodyContent", "add-instructor");
        return "master-template";
    }

    @PostMapping("/deleteInstructor/{id}")
    public String deleteInstructor(@PathVariable Long id) {
        this.instructorService.deleteById(id);
        return "redirect:/instructors";
    }

    @GetMapping("/instructors/edit-form/{id}")
    public String editInstructorPage(@PathVariable Long id, Model model) {
        List<Skills> skills = this.skillsService.findAll();
        model.addAttribute("skills", skills);
        if (this.instructorService.findById(id) != null) {
            Instructor instructor = this.instructorService.findById(id);
            model.addAttribute("instructor", instructor);
            model.addAttribute("bodyContent", "add-instructor");
            return "master-template";
        }
        return "redirect:/videos?error=VideoNotFound"; //???????????????????
    }

    @PostMapping("/instructors/add")
    public String saveInstructor(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam(required = false)  List<Skills> skills,
            @RequestParam String url) {
        if (id != null) {
            this.instructorService.update(id,name,surname,skills,url);
        } else {
            this.instructorService.save(name,surname,url);
        }
        return "redirect:/instructors";
    }
    @PostMapping("/addSkill/{id}")
    public String saveInstructor(
         @PathVariable Long id,
            @RequestParam List<Skills> skills) {
        this.instructorService.assignSkill(id,skills);
        return "redirect:/instructors";
    }
}