package com.example.QuanLySinhVien.dto.request;

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
    private String name;
    private LocalDate birthDate;
    private Double mark;
}
