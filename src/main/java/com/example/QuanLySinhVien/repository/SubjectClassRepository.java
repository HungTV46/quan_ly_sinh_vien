package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectClassRepository extends JpaRepository<SubjectClass,Long> {
    boolean existsByClassEntityId(Long classId);

    boolean existsBySubjectId(Long subjectId);
}
