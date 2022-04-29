package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import com.example.demo.model.VideoReleaseDate;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoReleaseDateRepository extends CrudRepository<VideoReleaseDate, Long> {

    @Query("from VideoReleaseDate e where not(e.end < :from or e.start > :to)")
    List<VideoReleaseDate> findBetween(@Param("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                       @Param("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to);

}