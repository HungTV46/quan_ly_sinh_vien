package com.example.QuanLySinhVien.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkFilterRequest {

    @Min(value = 0, message = "MARK_INVALID")
    @Max(value = 10,message = "MARK_INVALID")
    private Double mark;

    private String operator;
}
