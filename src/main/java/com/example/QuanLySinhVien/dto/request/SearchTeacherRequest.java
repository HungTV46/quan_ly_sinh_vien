package com.example.QuanLySinhVien.dto.request;

import com.example.QuanLySinhVien.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTeacherRequest {
    private String content;
    private String classId;
    private String email;
    private String phone;

    private PageDto pageDto;
}
