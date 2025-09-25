package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.entity.Student;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    @Query(value = "SELECT s FROM Student s WHERE UPPER(s.name) LIKE CONCAT('%', :name,'%')")
    List<Student> findStudentByName(@Param("name") String name);

    @Query(value = "select s from Student s where year(s.birthDate) = :year")
    List<Student> findStudentByYear(@Param("year") Integer year);

//    @Override
//    Optional<Student> findById(Long id);
    @Query(value = "select s from Student s join s.studentClasses c where c.classEntity.id = :id")
    List<Student> findStudentByClassId(@Param("id") Long id);

    @NonNull String findByUsername(String username);

    boolean existsByUsername(String username);
}
