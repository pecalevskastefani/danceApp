package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Instructor;
import com.example.demo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findAllByTitleLike(String title);
    List<Video> findAllByTitleLikeAndAndCategory(String title, Category category);
    List<Video> findAllByCategory(Category cateory);
    List<Video> findAllByInstructor(Instructor instructor);

}
