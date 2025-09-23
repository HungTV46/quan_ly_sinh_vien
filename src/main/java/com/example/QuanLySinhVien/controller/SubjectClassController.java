package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.SubjectClassRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.SubjectClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject-class")
public class SubjectClassController {
    private final SubjectClassService subjectClassService;

    @PostMapping
    public ApiResponse<?> create(@RequestBody SubjectClassRequest request) {
        return ApiResponse.builder()
                .result(subjectClassService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.builder()
                .result(subjectClassService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getById(@PathVariable Long id) {
        return ApiResponse.builder()
                .result(subjectClassService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody SubjectClassRequest request) {
        return ApiResponse.builder()
                .result(subjectClassService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        subjectClassService.delete(id);
        return ApiResponse.builder()
                .result("delete subject_class successfully")
                .build();
    }
}
