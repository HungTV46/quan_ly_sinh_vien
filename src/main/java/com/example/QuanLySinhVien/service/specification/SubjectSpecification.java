package com.example.QuanLySinhVien.service.specification;

import com.example.QuanLySinhVien.entity.ClassEntity;
import com.example.QuanLySinhVien.entity.Subject;
import com.example.QuanLySinhVien.entity.SubjectClass;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecification {
    public static Specification<Subject> hasName(String name){
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Subject> filterByStatus(Integer status){
        return (root, query, criteriaBuilder) ->{
            if (status == null){
                return null;
            }
            Join<Subject, SubjectClass> join = root.join("subjectClasses");
            Join<ClassEntity, SubjectClass>  classJoin = join.join("classEntity");
            return criteriaBuilder.equal(join.get("status"), status);
        };
    }

    public static Specification<Subject> getByClassId(Long classId){
        return (root, query, criteriaBuilder) ->{
            if (classId == null){
                return null;
            }
            Join<Subject, SubjectClass> join = root.join("subjectClasses");
            Join<ClassEntity, SubjectClass>  classJoin = join.join("classEntity");
            return criteriaBuilder.equal(join.get("id").get("classId"), classId);
        };
    }
}
