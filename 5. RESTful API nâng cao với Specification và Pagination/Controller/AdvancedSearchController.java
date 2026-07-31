package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.specification.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdvancedSearchController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // GET /api/students/search?name=John&ageFrom=18&ageTo=30&emailDomain=@gmail.com&page=0&size=10&sort=name,asc
    @GetMapping("/students/search")
    public Page<Student> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) String emailDomain,
            Pageable pageable) {

        return studentRepository.findAll(
                StudentSpecification.filterStudents(name, ageFrom, ageTo, emailDomain),
                pageable
        );
    }

    // GET /api/courses?page=0&size=5&sort=duration,desc
    @GetMapping("/courses")
    public Page<Course> getAllCoursesWithPagination(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}
