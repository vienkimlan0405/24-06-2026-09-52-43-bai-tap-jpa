package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // JPQL tìm khóa học có duration > hours
    @Query("SELECT c FROM Course c WHERE c.duration > :hours")
    List<Course> findByDurationGreaterThan(@Param("hours") Integer hours);
}
