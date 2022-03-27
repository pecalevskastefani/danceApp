package com.example.demo.service;

import com.example.demo.model.Favorites;
import com.example.demo.model.Video;

import java.util.List;

public interface FavoritesService {
    List<Video> listAllFavoriteVideos(Long videoId);
    Favorites getActiveFavorites(String email);
    Favorites addVideoToFavorites(String email, Long videoId);
    Favorites findById(Long id);
}
