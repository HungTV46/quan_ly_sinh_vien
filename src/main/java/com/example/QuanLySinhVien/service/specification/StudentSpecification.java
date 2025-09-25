package com.example.QuanLySinhVien.service.specification;

import com.example.QuanLySinhVien.entity.ClassEntity;
import com.example.QuanLySinhVien.entity.Student;
import com.example.QuanLySinhVien.entity.StudentClass;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentSpecification {
    public static Specification<Student> hasName(String name){
        return (root, criteriaQuery, criteriaBuilder) ->
                (name == null || name.isBlank()) ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%"+name.toLowerCase(Locale.ROOT)+"%");
    }

    public static Specification<Student> markGreaterThanOrEqual(Double markMin){
        return (root, criteriaQuery, criteriaBuilder) ->
                markMin == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("mark"), markMin);
    }

    public static Specification<Student> markLessThanOrEqual(Double markMax){
        return (root, query, cb) ->
                markMax == null ? null : cb.lessThanOrEqualTo(root.get("mark"), markMax);
    }

    public static Specification<Student> birthDateBetween(LocalDate from, LocalDate to){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (from != null && to != null) {
                return criteriaBuilder.between(root.get("birthDate"), from, to);
            } else if (from != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), from);
            } else if (to != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), to);
            }
            return null;
        };
    }

    public static Specification<Student> hasClassId (Long classId){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (classId == null) {
                return null;
            }
            Join<Student, StudentClass> scJoin = root.join("studentClasses");
            return criteriaBuilder.equal(scJoin.get("id").get("classId"), classId);
        };
    }

    public static Specification<Student> hasStudent(Long subjectId, Integer status){
        return (root, query, criteriaBuilder) -> {
            Join<Student,StudentClass> join1 = root.join("studentClasses");
            Join<ClassEntity,StudentClass> join2 = join1.join("classEntity");
            Join<ClassEntity,StudentClass> join3 = join2.join("subjectClasses");
            if (status == null && subjectId == null ) {
                return null;
            } else if (status == null) {
                return criteriaBuilder.equal(join3.get("subjectId"), subjectId);
            } else if (subjectId == null) {
                return criteriaBuilder.equal(join1.get("status"), status);
            } else {
                return criteriaBuilder.and(criteriaBuilder.equal(join1.get("status"),  status),
                        criteriaBuilder.equal(join3.get("subject").get("id"), subjectId));
            }
        };
    }

    public static Specification<Student> checkUserName(Long currentId, String userName){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (currentId != null) {
                predicates.add(criteriaBuilder.notEqual(root.get("id"),currentId));
            }

            if (userName != null && !userName.isEmpty()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("username")), userName.toLowerCase(Locale.ROOT)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
