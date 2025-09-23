package com.example.QuanLySinhVien.dto.request;

import com.example.QuanLySinhVien.entity.ClassEntity;
import lombok.Data;

@Data
public class TeacherRequest {
    private String name;
    private String email;
    private String address;
    private String phone;
    private ClassEntity classEntity;
}
