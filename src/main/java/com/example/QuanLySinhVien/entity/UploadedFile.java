package com.example.QuanLySinhVien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UPLOADED_FILE")
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "upload_file_seq")
    @SequenceGenerator(name = "upload_file_seq", sequenceName = "UPLOADED_FILE_SEQ",  allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "UPLOAD_TIME")
    private LocalDateTime uploadTime;
}
