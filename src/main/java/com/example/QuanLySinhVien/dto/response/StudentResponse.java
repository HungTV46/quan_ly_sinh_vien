package com.example.QuanLySinhVien.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
