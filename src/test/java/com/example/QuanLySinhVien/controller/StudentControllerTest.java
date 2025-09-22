package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.StudentRequest;
import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testCreateStudent() throws Exception {
        StudentRequest request = new StudentRequest();
        request.setName("truong van");
        request.setUsername("user1");
        request.setPassword("password1");
        request.setBirthDate(LocalDate.of(2000,2,25));
        request.setMark(3.5);

        StudentResponse response = new StudentResponse(1l, "user1", "password1", "truong van", LocalDate.of(2000,2,25), 3.5);

        when(studentService.createStudent(any(StudentRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("truong van"))
            .andExpect(jsonPath("$.birthDate").value("2000-02-25"))
            .andExpect(jsonPath("$.mark").value(3.5));

    }

    @Test
    void testCreateStudent_InvalidInput() throws Exception {
        StudentRequest request = new StudentRequest();
//        request.setName(null);
//        request.setUsername("user1");
//        request.setPassword("password1");
        request.setBirthDate(LocalDate.of(2000,2,25));
        request.setMark(3.0);

        mockMvc.perform(post("/api/v1/student")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateStudent_ServiceThrowsException() throws Exception {
        StudentRequest request = new StudentRequest();
        request.setName("truong van");
        request.setUsername("user1");
        request.setPassword("password1");
        request.setBirthDate(LocalDate.of(2000,2,25));
        request.setMark(3.5);
        when(studentService.createStudent(any(StudentRequest.class))).thenThrow(new AppException(ErrorCode.USERNAME_EXISTED));

        mockMvc.perform(post("/api/v1/student")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.USERNAME_EXISTED.getMessage()))
                .andExpect(jsonPath("$.code").value(ErrorCode.USERNAME_EXISTED.getCode()));

    }

    @Test
    void testCreateStudent_WrongContentType() throws Exception {
        mockMvc.perform(post("/api/v1/student")
                .contentType(MediaType.TEXT_PLAIN)
                .content("truong van"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<StudentResponse> list = List.of(
                new StudentResponse(1l, "user1", "password1", "truong van", LocalDate.of(2000,2,25), 3.5),
                new StudentResponse(2l, "user2", "password2", "truong van2", LocalDate.of(2000,12,25), 3.53)
        );

        when(studentService.getAllStudents()).thenReturn(list);

        mockMvc.perform(get("/api/v1/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("truong van"))
                .andExpect(jsonPath("$[0].birthDate").value("2000-02-25"))
                .andExpect(jsonPath("$[0].mark").value(3.5));
    }

    @Test
    void testGetAllStudents_EmptyList() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetAllStudents_ServiceThrowException() throws Exception {
        when(studentService.getAllStudents()).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/api/v1/student"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetStudentById() throws Exception {
        StudentResponse response = new StudentResponse(1l, "user1", "password1", "truong van", LocalDate.of(2000,2,25), 3.5);

        when(studentService.getStudentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/student/search-by-id?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("truong van"))
                .andExpect(jsonPath("$.birthDate").value("2000-02-25"))
                .andExpect(jsonPath("$.mark").value(3.5));
    }

    @Test
    void testGetStudentById_ServiceHandleException() throws Exception {
        when(studentService.getStudentById(1L)).thenThrow(new RuntimeException("student is not exist"));

        mockMvc.perform(get("/api/v1/student/search-by-id?id=1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteStudentById() throws Exception {
        mockMvc.perform(delete("/api/v1/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted successfully"));
    }

//    @Test
//    void testDeleteStudentById_ServiceHandleException() throws Exception {
//        Long id = 1L;
//        when(studentService.deleteStudent(id))
//        doThrow(new RuntimeException("student is not exist")).when(studentService).deleteStudent(id);
//
//        mockMvc.perform(delete("/api/v1/student?id=1"))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("student is not exist"));
//
//    }

    @Test
    void testUpdateStudent() throws Exception {
        StudentRequest request = new StudentRequest();
        request.setName("truong van");
        request.setUsername("user1");
        request.setPassword("password1");
        request.setBirthDate(LocalDate.of(2000,2,25));
        request.setMark(3.5);

        StudentResponse response = new StudentResponse(1L, "user1", "password1", "truong van", LocalDate.of(2000,2,25), 3.5);

        when(studentService.updateStudent(request,1L)).thenReturn(response);

        mockMvc.perform(put("/api/v1/student?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
//        .andExpect(jsonPath("$.id").value(1))
//        .andExpect(jsonPath("$.name").value("truong van"))
//        .andExpect(jsonPath("$.birthDate").value("2000-02-25"))
//        .andExpect(jsonPath("$.mark").value(3.5));
    }
}
