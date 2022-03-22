package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer {

    private final VideoService videoService;
    private final InstructorService instructorService;
    private final CategoryService categoryService;
    private final SkillsService skillsService;
    private final UserService userService;
    private final ProgramsService programsService;

    public DataInitializer(VideoService videoService,
                           InstructorService instructorService,
                           CategoryService categoryService,
                           SkillsService skillsService, UserService userService, ProgramsService programsService) {
        this.videoService = videoService;
        this.instructorService = instructorService;
        this.categoryService = categoryService;
        this.skillsService = skillsService;
        this.userService = userService;
        this.programsService = programsService;
    }

    private Role randomizeEventType(int i) {
        if (i % 3 == 0) return Role.ROLE_USER;
        else  return Role.ROLE_ADMIN;
    }

    @PostConstruct
    public void initData() {
        //KATEGORII
        this.categoryService.create("Hip-Hop","Hip-hop dance is a vibrant form of dance that combines a variety of freestyle movements to create a cultural piece of art.");
        this.categoryService.create("Jazz","Jazz dance is a form of dance that combines both African and European dance styles. This high-energy dance has a liveliness that sets it apart from traditional dance forms, such as classical ballet");
        this.categoryService.create("Contemporary","Contemporary is an important genre of dance performed in societies around the world, celebrated by people both young and old.");
        this.categoryService.create("Dancehall","Dancehall is a popular dance genre springing out of English speaking Caribbean, and spearheaded from Jamaica.");
        this.categoryService.create("Ballet","Ballet dance developed during the Italian Renaissance, before evolving in France and Russia into a concert dance meant for public performance.");
        this.categoryService.create("Warm-up","It's important to warm up before class or before a performance; do something cardiovascular to warm your body up and to increase your heart rate.");
        this.categoryService.create("Stretching","Stretching after dance lessons helps to eliminate this buildup from the body and reduce pain in the muscles.");
        List<Category> cateogries = this.categoryService.listAll();

        //SKILLS
        this.skillsService.create("Creative");
        this.skillsService.create("Self-discipline");
        this.skillsService.create("Patient");
        this.skillsService.create("Mentor");
        this.skillsService.create("Leadership skill");
        this.skillsService.create("Ability to explain effectively");
        List<Skills> skills = this.skillsService.findAll();

        //INSTRUKTORI
        this.instructorService.save("Stefani","Pecalevska", "images/instructor1.jpg");
        this.instructorService.save("Ana","Pejovska", "images/instructor2.jpg");
        this.instructorService.save("Jovana","Ivanovska","images/instructor3.jpg");
        List<Instructor> coaches = this.instructorService.findAll();


        //VIDEA
        this.videoService.create("justin bieber - baby","Koreografija na ana",
                cateogries.get(5), coaches.get(0),
                "images/video4.jpg");
        this.videoService.create("50 cent - in da club","Koreografija na stefi",
                cateogries.get(0), coaches.get(0), "images/video1.jpg");
        this.videoService.create("koffee - rapture","Koreografija na jovana",
                cateogries.get(3), coaches.get(0),
                "images/video5.jpg");
        this.videoService.create("ariana grande - monster","Koreografija na stefi",
                cateogries.get(0), coaches.get(0),
                "images/video2.jpg");

        this.videoService.create("balet - baletska pesna","Koreografija na ana",
                cateogries.get(4), coaches.get(1),
                "images/video3.jpg");

        this.videoService.create("jazz - jazz pesna","Koreografija na jovana",
                cateogries.get(1), coaches.get(2), "images/video6.jpg");

        for (int i = 1; i < 11; i++) {
            this.userService.create(
                    "user." + i + "@gmail.com",
                    "user" + i,
                    "User"+i,
                    "Surname"+i,
                    this.randomizeEventType(i)
            );
        }

        this.programsService.save("Monthly Plan", "For $14.99 per month ($179.99 paid yearly) you get 24/7 access to all G&G content.",14.99, "https://alpha.uscreencdn.com/images/offer/36783/big_12796_2Foffer_image_2F70csReQZRLihJZ3OkTj5_Payment_20Thumbnails_20-_20MONTHLY.jpg",new ArrayList<User>());
        this.programsService.save("3 Month Plan", "For $11.99 per month ($143.99 paid yearly) you get 24/7 access to all G&G content.",11.99, "https://alpha.uscreencdn.com/images/offer/59916/big_Payment_Plan_-_3_Months_v2.1629129860.jpeg",new ArrayList<User>());
        this.programsService.save("Annual Plan", "For $5.83 per month ($69.99 paid yearly) you get 24/7 access to all G&G content.",5.83, "https://alpha.uscreencdn.com/images/offer/35765/big_Payment_Plan_-_Yearly_v2.1629129904.jpeg",new ArrayList<User>());
    }
}
