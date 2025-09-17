package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.StudentRequest;
import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(studentService.createStudent(any(StudentRequest.class))).thenThrow(new RuntimeException("username is exist"));

        mockMvc.perform(post("/api/v1/student")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

    }

}
