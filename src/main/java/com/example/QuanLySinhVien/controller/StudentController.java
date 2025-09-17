package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.MarkFilterRequest;
import com.example.QuanLySinhVien.dto.request.StudentRequest;
import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.entity.Student;
import com.example.QuanLySinhVien.mapper.StudentMapper;
import com.example.QuanLySinhVien.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> createStudent(@Validated @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.createStudent(studentRequest));
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/search-by-id")
    public ResponseEntity<?> getStudentById(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/filter-good-student")
    public ResponseEntity<?> filterGoodStudent(@RequestBody MarkFilterRequest markFilterRequest) {
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

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestParam Long id, @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.updateStudent(studentRequest, id));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @GetMapping("/export-json-file")
    public ResponseEntity<?> exportStudentsJson() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.json")
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
}
