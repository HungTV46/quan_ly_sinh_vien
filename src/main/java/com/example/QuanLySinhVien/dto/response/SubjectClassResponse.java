package com.example.QuanLySinhVien.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubjectClassResponse {
    private Long subjectId;
    private Long classId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}
