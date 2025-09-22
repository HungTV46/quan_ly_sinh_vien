package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.PageDto;
import com.example.QuanLySinhVien.dto.request.ClassRequest;
import com.example.QuanLySinhVien.dto.request.SearchClassRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
@Tag(name = "Class API", description = "quản lý danh sách class")
public class ClassController {

    private final ClassService classService;

    @Operation(summary = "tạo class", description = "tạo class")
    @PostMapping
    public ApiResponse<?> createClass(@Valid @RequestBody ClassRequest classRequest) {
        return ApiResponse.builder()
                .result(classService.createClass(classRequest))
                .build();
    }

    @PostMapping("/find-all")
    public ApiResponse<?> getClass(@RequestBody PageDto pageDto) {
        return ApiResponse.builder()
                .result(classService.getAllClasses(pageDto.toPageable()))
                .build();
    }
    @GetMapping("/search-by-id/{id}")
    public ApiResponse<?> getClassById(@PathVariable Long id) {
        return ApiResponse.builder()
                .result(classService.getClassById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateClass(@PathVariable Long id ,@Valid @RequestBody ClassRequest classRequest) {
        return ApiResponse.builder()
                .result(classService.updateClassById(id,classRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteClassById(@PathVariable Long id) {
        classService.deleteClassById(id);
        return ApiResponse.builder()
                .result("Delete class successfully")
                .build();
    }

    @PostMapping("/find-by-class-name")
    public ApiResponse<?> getClassByName(@RequestBody SearchClassRequest request) {
        return ApiResponse.builder()
                .result(classService.findByClassName(request))
                .build();
    }
}
