package com.example.QuanLySinhVien.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TeacherRequest {
    @NotBlank(message = "NAME_INVALID")
    private String name;

    @Email(message = "EMAIL_INVALID")
    private String email;

    private String address;

    @Pattern(regexp = "^[0-9]{10}$",message = "PHONE_INVALID")
    private String phone;

    private Long classId;
}
