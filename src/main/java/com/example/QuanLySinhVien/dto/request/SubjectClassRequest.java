package com.example.QuanLySinhVien.dto.request;

import lombok.Data;

@Data
public class SubjectClassRequest {
    private Long subjectId;
    private Long classId;
    private Integer status;
}
