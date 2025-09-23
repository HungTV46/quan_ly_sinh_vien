package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByName(String name);
}
