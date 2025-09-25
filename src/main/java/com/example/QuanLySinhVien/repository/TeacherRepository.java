package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
    boolean existsByEmail(String email);
}
