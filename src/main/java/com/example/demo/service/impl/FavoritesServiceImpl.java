package com.example.demo.service.impl;


import com.example.demo.model.Favorites;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.model.exceptions.FavoritesNotFoundException;
import com.example.demo.model.exceptions.VideoAlreadyInFavoritesException;
import com.example.demo.repository.FavoritesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideoRepository;
import com.example.demo.service.FavoritesService;
import com.example.demo.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesServiceImpl  implements FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final VideoService videoService;
    private final UserRepository userRepository;


    public FavoritesServiceImpl(FavoritesRepository favoritesRepository, VideoService videoService, UserRepository userRepository) {
        this.favoritesRepository = favoritesRepository;
        this.videoService = videoService;

        this.userRepository = userRepository;
    }

    @Override
    public List<Video> listAllFavoriteVideos(Long faveId) {
        if(!this.favoritesRepository.findById(faveId).isPresent())
            throw new FavoritesNotFoundException(faveId);
        return this.favoritesRepository.findById(faveId).get().getVideos();
    }




    @Override
    public Favorites getActiveFavorites(String email) {
        User user = this.userRepository.findByUsername(email);
        return this.favoritesRepository
                .findByUser(user)
                .orElseGet(() -> {
                    Favorites favorite = new Favorites(user);
                    return this.favoritesRepository.save(favorite);
                });
    }


    @Override
    public Favorites addVideoToFavorites(String email, Long videoId) {
        Favorites favorites = this.getActiveFavorites(email);
        Video video = this.videoService.findById(videoId);
        if (favorites.getVideos()!=null && favorites.getVideos()
                    .stream().filter(i -> i.getId().equals(videoId))
                    .collect(Collectors.toList()).size() > 0) {
            throw new VideoAlreadyInFavoritesException(videoId, email);
        }
        favorites.getVideos().add(video);
       return this.favoritesRepository.save(favorites);
    }

   /* @Override
    /*public void deleteById(Long id) {
      List<Favorites> favorites = this.favoritesRepository.findAll();
      List<Video> videos = new ArrayList<>();
        for(Favorites f:favorites){
            for(Video v: f.getVideos() )
            if(v.getId().equals(id)){
                f.getVideos().remove(v);
            }
        }
    }*/

    @Override
    public Favorites findById(Long id) {
        return this.favoritesRepository.findById(id).orElseThrow(()->new FavoritesNotFoundException(id));
    }
}
