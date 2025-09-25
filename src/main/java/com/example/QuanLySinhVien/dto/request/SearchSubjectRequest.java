package com.example.QuanLySinhVien.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchSubjectRequest {
    private String name;
    private Integer status;
    private Long classId;
}
