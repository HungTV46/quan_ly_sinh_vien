package com.example.QuanLySinhVien.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentResponse {
    private Long id;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;
    private String name;
    private LocalDate birthDate;
    private Double mark;
}
