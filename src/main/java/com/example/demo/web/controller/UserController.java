package com.example.demo.web.controller;

import com.example.demo.model.User;
import com.example.demo.model.fileupload.FileUploadUtil;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/profile")
    public String getProfilePage(Model model, HttpServletRequest req) {
        String username = req.getRemoteUser();
        User user = this.userService.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("bodyContent", "profile");
        return "master-template";
    }
    @GetMapping("/cancelProgram")
    public String cancelProgram( HttpServletRequest req){
        String username = req.getRemoteUser();
        User user = this.userService.findByUsername(username);
        this.userService.cancelProgram(user);
        return "redirect:profile";
    }
    @PostMapping("/users/save")
    public RedirectView addImage(HttpServletRequest req,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String username = req.getRemoteUser();
        User user = this.userService.findByUsername(username);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);

       // User savedUser = userService.create(user.getUsername(),user.getPassword(),user.getName(),user.getSurname()
      //  ,user.getRole(),user.getBirthday());
        User savedUser = this.userService.save(user);
        String uploadDir = "user-photos/" + savedUser.getUsername();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/profile", true);
    }

}
