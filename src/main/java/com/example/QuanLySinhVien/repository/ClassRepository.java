package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassRepository extends JpaRepository<ClassEntity,Long> {
    boolean existsByClassName(String className);

    @Override
    Page<ClassEntity> findAll(Pageable pageable);

    @Query("select c from ClassEntity c where upper(c.className) like upper(concat('%',:className, '%'))")
    Page<ClassEntity> findByClassName(String className, Pageable pageable);
}
