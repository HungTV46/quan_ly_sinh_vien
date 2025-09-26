package com.example.QuanLySinhVien.dto.request;

import com.example.QuanLySinhVien.validation.ValidUsername;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentRequest {
    @ValidUsername(message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "PASSWORD_INVALID")
    @Size(min = 6, max = 15, message = "PASSWORD_LENGTH_INVALID")
    private String password;

    @NotBlank(message = "NAME_INVALID")
    private String name;
    private LocalDate birthDate;

    @Min(value = 0, message = "MARK_INVALID")
    @Max(value = 10,message = "MARK_INVALID")
    private Double mark;
}
