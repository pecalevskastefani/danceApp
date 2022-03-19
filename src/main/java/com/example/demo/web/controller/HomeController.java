package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
   @GetMapping("/home")
    public String getHomePage(Model model) {
       model.addAttribute("bodyContent", "home");
       return "master-template";
    }

    /* @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
       model.addAttribute("bodyContent", "access_denied");
       return "master-template";
   }*/
}
