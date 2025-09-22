package com.example.QuanLySinhVien.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassRequest {
    @NotBlank(message = "CLASSNAME_NOTBlANK")
    private String className;
}
