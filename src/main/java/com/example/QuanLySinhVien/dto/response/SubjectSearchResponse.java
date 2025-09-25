package com.example.QuanLySinhVien.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectSearchResponse {
    private Long id;
    private String name;
    private List<SubjectClassSearchResponse> classes;


}

