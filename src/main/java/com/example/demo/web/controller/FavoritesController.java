package com.example.demo.web.controller;

import com.example.demo.model.Favorites;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.service.FavoritesService;
import com.example.demo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FavoritesController {

   private final FavoritesService favoritesService;
   private final UserService userService;

    public FavoritesController(FavoritesService favoritesService, UserService userService) {
        this.favoritesService = favoritesService;
        this.userService = userService;
    }

    @GetMapping("/favorites")
    public String getFavoritesPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String email = req.getRemoteUser();
        Favorites  favorite = this.favoritesService.getActiveFavorites(email);
        req.getSession().setAttribute("fave",favorite.getId());

        List<Video> videos = this.favoritesService.listAllFavoriteVideos(favorite.getId());
        model.addAttribute("favorites", videos);
        model.addAttribute("bodyContent", "favorites");
        return "master-template";
    }

    @PostMapping("/favorites/{id}")
    public String addVideoToFavorites(
            @PathVariable Long id,HttpServletRequest request) {
        try {
            String email = (String) request.getSession().getAttribute("user");
            User user = this.userService.findByUsername(email);
            this.favoritesService.addVideoToFavorites(user.getUsername(), id);
            return "redirect:/catalog";
        } catch (RuntimeException exception) {
            return "redirect:/favorites?error=" + exception.getMessage();
        }
    }

}

