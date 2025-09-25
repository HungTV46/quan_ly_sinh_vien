package com.example.QuanLySinhVien.service.specification;

import com.example.QuanLySinhVien.entity.Teacher;
import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecification {
    public static Specification<Teacher> hasContent(String content){
        return (root, query, criteriaBuilder) ->{
            if (content == null){
                return null;
            }
            return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + content.toLowerCase() + "%" ),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + content.toLowerCase() + "%"));
        };
    }

    public static Specification<Teacher> hasClassId(String classId){
        return (root, query, criteriaBuilder) ->
                classId == null ? null : criteriaBuilder.equal(root.get("classEntity").get("id"), classId);
    }

    public static Specification<Teacher> hasEmail(String email){
        return (root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%"+email.toLowerCase()+"%");
    }

    public static Specification<Teacher> hasPhone(String phone){
        return (root, query, criteriaBuilder) ->
                phone == null ? null : criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }
}
