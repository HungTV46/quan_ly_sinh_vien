package com.example.QuanLySinhVien.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    @Builder.Default
    private int code = 1;
    private String message;
    private T result;
    private List<ErrorItem> errorItems;

    @Data
    @AllArgsConstructor
    public static class ErrorItem{
        private String field;
        private int code;
        private String message;
    }
}
