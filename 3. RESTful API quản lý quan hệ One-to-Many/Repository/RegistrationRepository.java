package com.example.demo.repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Registration;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    @Query("SELECT r.course FROM Registration r WHERE r.student.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT r.student FROM Registration r WHERE r.course.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);
}
