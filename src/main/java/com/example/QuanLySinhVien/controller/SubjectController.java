package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.SubjectRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.SubjectService;
import com.example.QuanLySinhVien.service.subjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ApiResponse<?> create(@RequestBody SubjectRequest request) {
        return ApiResponse.builder()
                .result(subjectService.createSubject(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.builder()
                .result(subjectService.getAllSubjects())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getById(@PathVariable Long id) {
        return ApiResponse.builder()
                .result(subjectService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody SubjectRequest request) {
        return ApiResponse.builder()
                .result(subjectService.updateSubject(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ApiResponse.builder()
                .result("delete subject successfully")
                .build();
    }
}
