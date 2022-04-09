package com.example.demo.web.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Instructor;
import com.example.demo.model.Video;
import com.example.demo.service.CategoryService;
import com.example.demo.service.InstructorService;
import com.example.demo.service.UserService;
import com.example.demo.service.VideoService;
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
public class CatalogController {
    private final VideoService videoService;
    private final CategoryService categoryService;
    private final InstructorService instructorService;
    private final UserService userService;


    public CatalogController(VideoService videoService,
                             CategoryService categoryService,
                             InstructorService instructorService, UserService userService) {
        this.videoService = videoService;
        this.categoryService = categoryService;
        this.instructorService = instructorService;
        this.userService = userService;
    }

    @GetMapping("/catalog")
    public String getCatalogPage(@RequestParam(required = false)String title,
                                 @RequestParam(required = false)Long category,
                                 @RequestParam(required = false)String error,
                                 Model model, HttpServletRequest req) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String email = req.getRemoteUser();
        req.getSession().setAttribute("program",this.userService.findByUsername(email).getProgram());
        List<Video> videos = new ArrayList<>();
        if(title!=null || category!=null){
            videos= this.videoService.filter(title,category);
        }
        else {
            videos= this.videoService.findAll();
        }
        model.addAttribute("videos",videos);
        model.addAttribute("categories", this.categoryService.listAll());
        model.addAttribute("user",this.userService.findByUsername(email));
        model.addAttribute("bodyContent", "catalog");
        return "master-template";
    }

    @PostMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Long id) {
        this.videoService.deleteById(id);
        return "redirect:/catalog";
    }
    @GetMapping("/add-form")
    public String addVideoPage(Model model) {
        List<Category> categories = this.categoryService.listAll();
        List<Instructor> instructors = this.instructorService.findAll();
        model.addAttribute("instructors",instructors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-video");
        return "master-template";
    }
    @GetMapping("/edit-form/{id}")
    public String editVideoPage(@PathVariable Long id, Model model) {
        if (this.videoService.findById(id) != null) {
            Video video= this.videoService.findById(id);
            List<Category> categories = this.categoryService.listAll();
            List<Instructor> instructors= this.instructorService.findAll();
            model.addAttribute("instructors",instructors);
            model.addAttribute("categories", categories);
            model.addAttribute("video", video);
            model.addAttribute("bodyContent", "add-video");
            return "master-template";
        }
        return "redirect:/videos?error=VideoNotFound";
    }
    @PostMapping("/add")
    public String saveVideo(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Long categoryId,
            @RequestParam Long coachId,
            @RequestParam String url) {
        if (id != null) {
            this.videoService.update(id,title,description,categoryId,coachId,url);
        } else {
            Category category = this.categoryService.findById(categoryId);
            Instructor coach = this.instructorService.findById(coachId);
            this.videoService.create(title,description,category,coach, url);
        }
        return "redirect:/catalog";
    }
}
