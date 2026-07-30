package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Registration;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OneToManyRegistrationController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // POST /api/students/{studentId}/courses: Thêm một danh sách khóa học mà sinh viên đăng ký
    @PostMapping("/students/{studentId}/courses")
    public ResponseEntity<?> registerCoursesForStudent(@PathVariable Long studentId, @RequestBody List<Long> courseIds) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) return ResponseEntity.notFound().build();

        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                Registration registration = new Registration(student, course, LocalDate.now());
                registrationRepository.save(registration);
            }
        }
        return ResponseEntity.ok("Đã đăng ký danh sách khóa học thành công.");
    }

    // GET /api/students/{studentId}/courses: Lấy danh sách các khóa học sinh viên đã đăng ký
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationRepository.findCoursesByStudentId(studentId));
    }

    // GET /api/courses/{courseId}/students: Lấy danh sách sinh viên đăng ký vào khóa học cụ thể
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationRepository.findStudentsByCourseId(courseId));
    }
}
