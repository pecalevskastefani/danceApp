package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Favorites;
import com.example.demo.model.Instructor;
import com.example.demo.model.Video;
import com.example.demo.model.exceptions.CategoryIdNotFoundException;
import com.example.demo.model.exceptions.InstructorIdNotFoundException;
import com.example.demo.model.exceptions.VideoNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FavoritesRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.VideoRepository;
import com.example.demo.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.http.client.methods.RequestBuilder.delete;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final InstructorRepository instructorRepository;
    private final CategoryRepository categoryRepository;
    private final FavoritesRepository favoritesRepository;
    public VideoServiceImpl(VideoRepository videoRepository, InstructorRepository instructorRepository, CategoryRepository categoryRepository, FavoritesRepository favoritesRepository) {
        this.videoRepository = videoRepository;
        this.instructorRepository = instructorRepository;


        this.categoryRepository = categoryRepository;
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public List<Video> findAll() {
        return this.videoRepository.findAll();
    }

    @Override
    public Video findById(Long id) {
        return this.videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        List<Favorites> favorites = this.favoritesRepository.findAll();
        List<Video> videos = new ArrayList<>();
        for(Favorites f:favorites){
            for(Video v: f.getVideos() )
                if(v.getId().equals(id)){
                    f.getVideos().removeIf(i->i.getId().equals(id));
                    break;
                }
        }
        this.videoRepository.deleteById(id);
    }

    @Override
    public Video create(String title, String description, Category category, Instructor instructor,String url) {
        return this.videoRepository.save(new Video(title,description,category,instructor,url));
    }

    @Override
    public Video update(Long id, String title, String description, Long category, Long instructor,String url) {
        Video video = this.findById(id);
        video.setTitle(title);
        video.setDescription(description);
        Category cat = this.categoryRepository.findById(category).orElseThrow(()->new CategoryIdNotFoundException(category));
        Instructor instructor1 = this.instructorRepository.findById(instructor)
                .orElseThrow(()-> new InstructorIdNotFoundException(instructor));
        video.setCategory(cat);
        video.setInstructor(instructor1);
        return this.videoRepository.save(video);

    }
    @Override
    public List<Video> filter(String title, Long categoryId) {
        title= title.toLowerCase();
        String title1 = '%' + title + '%';
        title1 = title1.toLowerCase();
        Category cat = categoryId != null ? this.categoryRepository.findById(categoryId).orElse((Category)null):null;
        if(title!=null && categoryId!=null){
            return this.videoRepository.findAllByTitleLikeAndAndCategory(title1,cat);
        }
        else if(title!=null){
            return this.videoRepository.findAllByTitleLike(title1);
        }
        else if(categoryId != null){
            return this.videoRepository.findAllByCategory(cat);
        }
        else {
            return this.findAll();
        }
    }

    @Override
    public List<Video> findAllByInstructor(Instructor instructor) {
        return this.videoRepository.findAllByInstructor(instructor);
    }
}
