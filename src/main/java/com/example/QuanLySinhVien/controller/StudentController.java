package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.MarkFilterRequest;
import com.example.QuanLySinhVien.dto.request.SearchStudentRequest;
import com.example.QuanLySinhVien.dto.request.StudentRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest studentRequest,
                                           @RequestHeader(value = "Idempotency-key", required = false) String idempotencyKey) throws JsonProcessingException {
        return ResponseEntity.ok(studentService.createStudent(studentRequest,idempotencyKey));
    }

    @GetMapping
    public ApiResponse<?> getAllStudents() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("UserName : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.builder()
                .result(studentService.getAllStudents())
                .build();
    }

    @GetMapping("/search-by-id")
    public ResponseEntity<?> getStudentById(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/filter-good-student")
    public ResponseEntity<?> filterGoodStudent(@Valid @RequestBody MarkFilterRequest markFilterRequest) {
        return ResponseEntity.ok(studentService.filterMarkGoodStudent(markFilterRequest));
    }

    @GetMapping("/sort-student")
    public ResponseEntity<?> sortStudent(@RequestParam(required = false) List<String> sort) {
        return ResponseEntity.ok(studentService.sortStudents(sort));
    }

    @PostMapping("/search-by-name")
    public ResponseEntity<?> getStudentByName(@RequestBody String name) {
        return ResponseEntity.ok(studentService.getStudentByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.updateStudent(studentRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @GetMapping("/export-json-file")
    public ResponseEntity<?> exportStudentsJson() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.json")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(students);
    }

    @GetMapping("/mark-highest-or-lowest")
    public ResponseEntity<?> getHighestOrLowestStudent(@RequestParam String status) {
        return ResponseEntity.ok(studentService.searchStudentByMark(status));
    }

    @GetMapping("/group-by-year")
    public ResponseEntity<?> getGroupByYear(@RequestParam Integer year) {
        return ResponseEntity.ok(studentService.findStudentByYear(year));
    }

    @GetMapping("/average-mark")
    public ResponseEntity<?> getAverageMark() {
        return ResponseEntity.ok(studentService.getAverageMark());
    }

    @GetMapping("/list-student/{id}")
    public ResponseEntity<?> getStudentByClassId(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentByClassId(id));
    }

    @PostMapping("/search")
    public ApiResponse<?> searchStudent(@RequestBody SearchStudentRequest request) {
        return ApiResponse.builder()
                .result(studentService.searchStudents(request))
                .build();
    }
}
