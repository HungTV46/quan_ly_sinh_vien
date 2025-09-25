package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {
    boolean existsByName(String name);
}
