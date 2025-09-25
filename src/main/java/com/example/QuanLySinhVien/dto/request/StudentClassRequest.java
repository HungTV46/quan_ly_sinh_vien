package com.example.QuanLySinhVien.dto.request;

import lombok.Data;

@Data
public class StudentClassRequest {
    private Long studentId;
    private Long classId;
    private Integer status;
}
