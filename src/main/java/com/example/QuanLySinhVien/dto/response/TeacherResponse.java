package com.example.QuanLySinhVien.dto.response;

import com.example.QuanLySinhVien.entity.ClassEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private ClassEntity classEntity;
}
