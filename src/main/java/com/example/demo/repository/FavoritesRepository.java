package com.example.demo.repository;

import com.example.demo.model.Favorites;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites,Long> {
    List<Video> findAllById(Long videoId);
    Optional<Favorites> findByUser(User user);
}
