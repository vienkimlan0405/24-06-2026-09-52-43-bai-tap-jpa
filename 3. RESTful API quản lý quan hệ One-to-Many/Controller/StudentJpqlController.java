package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentJpqlController {

    @Autowired
    private StudentRepository studentRepository;

    // GET /api/students/search?name={keyword}
    @GetMapping("/search")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        return studentRepository.findByNameContainingKeyword(name);
    }
}
