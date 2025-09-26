package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.StudentClass;
import com.example.QuanLySinhVien.entity.embedded.StudentClassId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudentClassRepository extends JpaRepository<StudentClass, StudentClassId> {
    boolean existsByClassEntityId(Long classEntityId);

    boolean existsByStudentId(Long studentId);

    @Query("select s from StudentClass s where s.classEntity.id = :classId and s.student.id = :studentId")
    StudentClass findByClassIdAndStudentId(Long classId, Long studentId);

    @Query("select sc from StudentClass sc where sc.classEntity.id = :classEntityId")
    List<StudentClass> findByClassEntityId(Long classEntityId);
}
