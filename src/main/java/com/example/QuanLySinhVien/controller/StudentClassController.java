package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.StudentClassRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.StudentClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student-class")
public class StudentClassController {
    private final StudentClassService studentClassService;

    @PostMapping
    public ApiResponse<?> create(@RequestBody StudentClassRequest request) {
        return ApiResponse.builder()
                .result(studentClassService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.builder()
                .result(studentClassService.getAll())
                .build();
    }

    @GetMapping("/get-by-class-id/{classId}")
    public ApiResponse<?> getByClassId(@PathVariable Long classId) {
        return ApiResponse.builder()
                .result(studentClassService.getByClassId(classId))
                .build();
    }

    @GetMapping("/get-by-id")
    public ApiResponse<?> getById(@RequestParam Long studentId, @RequestParam Long classId) {
        return ApiResponse.builder()
                .result(studentClassService.getById(studentId, classId))
                .build();
    }

    @PutMapping
    public ApiResponse<?> update(@Valid @RequestBody StudentClassRequest request) {
        return ApiResponse.builder()
                .result(studentClassService.update(request))
                .build();
    }

    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam Long studentId, @RequestParam Long classId) {
        studentClassService.delete(studentId, classId);
        return ApiResponse.builder()
                .result("delete student_class successfully")
                .build();
    }
}
