package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.SubjectClassRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.SubjectClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject-class")
public class SubjectClassController {
    private final SubjectClassService subjectClassService;

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody SubjectClassRequest request) {
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

    @GetMapping("/get-by-class-id/{classId}")
    public ApiResponse<?> getByClassId(@PathVariable Long classId) {
        return ApiResponse.builder()
                .result(subjectClassService.getByClassId(classId))
                .build();
    }

    @GetMapping("/get-by-id")
    public ApiResponse<?> getById(@RequestParam Long subjectId, @RequestParam Long classId) {
        return ApiResponse.builder()
                .result(subjectClassService.getById(subjectId, classId))
                .build();
    }

    @PutMapping
    public ApiResponse<?> update(@RequestBody SubjectClassRequest request) {
        return ApiResponse.builder()
                .result(subjectClassService.update(request))
                .build();
    }

    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam Long subjectId,@RequestParam Long classId) {
        subjectClassService.delete(subjectId, classId);
        return ApiResponse.builder()
                .result("delete subject_class successfully")
                .build();
    }
}
