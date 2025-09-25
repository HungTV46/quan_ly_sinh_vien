package com.example.QuanLySinhVien.dto.request;

import com.example.QuanLySinhVien.dto.PageDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchStudentRequest {
    private String name;
    private Double markMin;
    private Double markMax;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private Long classId;
    private Long subjectId;
    private Integer status;
    private PageDto pageDto;
}
