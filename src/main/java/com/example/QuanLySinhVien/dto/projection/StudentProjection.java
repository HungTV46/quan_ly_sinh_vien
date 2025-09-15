package com.example.QuanLySinhVien.dto.projection;


import java.time.LocalDate;

public interface StudentProjection {
    Long getId();
    String getName();
    LocalDate getBirthDate();
    Double getMark();
}
