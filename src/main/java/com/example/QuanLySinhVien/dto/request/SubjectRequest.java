package com.example.QuanLySinhVien.dto.request;

import com.example.QuanLySinhVien.entity.ClassEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectRequest {
    @NotBlank(message = "NAME_INVALID")
    private String name;
}
