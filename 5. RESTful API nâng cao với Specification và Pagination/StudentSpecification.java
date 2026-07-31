package com.example.demo.specification;

import com.example.demo.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> filterStudents(String name, Integer ageFrom, Integer ageTo, String emailDomain) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Tên chứa từ khóa
            if (name != null && !name.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            // Khoảng tuổi (ageFrom -> ageTo)
            if (ageFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("age"), ageFrom));
            }

            if (ageTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("age"), ageTo));
            }

            // Miền email
            if (emailDomain != null && !emailDomain.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + emailDomain));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
