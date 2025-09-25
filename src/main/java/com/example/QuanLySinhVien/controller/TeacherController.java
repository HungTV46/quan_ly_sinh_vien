package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.TeacherRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.dto.response.TeacherResponse;
import com.example.QuanLySinhVien.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody TeacherRequest request) {
        return ApiResponse.builder()
                .result(teacherService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.builder()
                .result(teacherService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherResponse> getById(@PathVariable @Valid Long id) {
        return ApiResponse.<TeacherResponse>builder()
                .result(teacherService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody TeacherRequest request) {
        return ApiResponse.builder()
                .result(teacherService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ApiResponse.builder()
                .result("delete teacher successfully")
                .build();
    }
}
