package com.example.QuanLySinhVien.repository;


import com.example.QuanLySinhVien.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
}
