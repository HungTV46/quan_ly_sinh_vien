package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.SubjectClass;
import com.example.QuanLySinhVien.entity.embedded.StudentClassId;
import com.example.QuanLySinhVien.entity.embedded.SubjectClassId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectClassRepository extends JpaRepository<SubjectClass, SubjectClassId> {
    boolean existsByClassEntityId(Long classId);

    boolean existsBySubjectId(Long subjectId);

    @Query("select sc from SubjectClass sc where sc.classEntity.id = :classId and sc.subject.id = :subjectId")
    SubjectClass findByClassIdAndSubjectId(Long classId, Long subjectId);

    SubjectClass findBySubjectId(Long subjectId);

    List<SubjectClass> findByClassEntityId(Long classEntityId);

}
