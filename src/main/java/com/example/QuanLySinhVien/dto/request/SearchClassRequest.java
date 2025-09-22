package com.example.QuanLySinhVien.dto.request;

import com.example.QuanLySinhVien.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchClassRequest {
    private String className;
    private PageDto pageDto;

}
