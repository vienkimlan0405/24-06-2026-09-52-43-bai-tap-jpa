package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseJpqlController {

    @Autowired
    private CourseRepository courseRepository;

    // GET /api/courses?durationGreaterThan={hours}
    @GetMapping(params = "durationGreaterThan")
    public List<Course> getCoursesByDurationGreaterThan(@RequestParam Integer durationGreaterThan) {
        return courseRepository.findByDurationGreaterThan(durationGreaterThan);
    }

    // GET /api/courses/count: Trả về số lượng khóa học
    @GetMapping("/count")
    public Long getCourseCount() {
        return courseRepository.count();
    }
}
