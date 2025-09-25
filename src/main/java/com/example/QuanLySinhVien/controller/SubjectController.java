package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.SearchSubjectRequest;
import com.example.QuanLySinhVien.dto.request.SubjectRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody SubjectRequest request) {
        return ApiResponse.builder()
                .result(subjectService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.builder()
                .result(subjectService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getById(@PathVariable Long id) {
        return ApiResponse.builder()
                .result(subjectService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        return ApiResponse.builder()
                .result(subjectService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ApiResponse.builder()
                .result("delete subject successfully")
                .build();
    }

    @PostMapping("/search")
    public ApiResponse<?> search(@RequestBody SearchSubjectRequest request){
        return ApiResponse.builder()
                .result(subjectService.search(request))
                .build();
    }
}
