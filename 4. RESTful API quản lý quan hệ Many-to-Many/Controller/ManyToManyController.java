package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Registration;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ManyToManyController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // POST /api/students/{studentId}/courses/{courseId}: Thêm sinh viên vào khóa học
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<?> registerSingleCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student == null || course == null) {
            return ResponseEntity.notFound().build();
        }

        Registration registration = new Registration(student, course, LocalDate.now());
        registrationRepository.save(registration);
        return ResponseEntity.ok("Thêm sinh viên vào khóa học thành công.");
    }

    // DELETE /api/students/{studentId}/courses/{courseId}: Hủy đăng ký khỏi khóa học
    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    @Transactional
    public ResponseEntity<?> unregisterCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        registrationRepository.deleteByStudentIdAndCourseId(studentId, courseId);
        return ResponseEntity.ok("Đã hủy đăng ký khỏi khóa học.");
    }

    // GET /api/courses/{courseId}/students: Lấy danh sách tất cả sinh viên trong khóa học
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsInCourse(@PathVariable Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationRepository.findStudentsByCourseId(courseId));
    }

    // GET /api/students/{studentId}/courses: Lấy danh sách các khóa học sinh viên tham gia
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getCoursesOfStudent(@PathVariable Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationRepository.findCoursesByStudentId(studentId));
    }
}
