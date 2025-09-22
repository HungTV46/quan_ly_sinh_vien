package com.example.QuanLySinhVien.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentRequest {
    @NonNull
    private String username;

    @NonNull
    private String password;

    @NotBlank(message = "Tên bắt buộc phải điền")
    private String name;
    private LocalDate birthDate;
    private Double mark;
}
