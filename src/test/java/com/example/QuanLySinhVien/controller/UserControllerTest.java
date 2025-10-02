package com.example.QuanLySinhVien.controller;


import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.PermissionResponse;
import com.example.QuanLySinhVien.dto.response.RoleResponse;
import com.example.QuanLySinhVien.dto.response.UserResponse;
import com.example.QuanLySinhVien.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRequest  request;
    private UserResponse userResponse;
    private RoleResponse roleResponse;

    @BeforeEach
    void initData() {
        request = UserRequest.builder()
                .username("user")
                .password("user")
                .roles(List.of("USER"))
                .build();

        roleResponse = RoleResponse.builder()
                .name("USER")
                .description("role user")
                .permissions(Set.of(
                        PermissionResponse.builder().name("APPROVE_POST").description("approve post data permission").build(),
                        PermissionResponse.builder().name("CREATE_DATA").description("Create data permission").build()
                ))
                .build();

        userResponse = UserResponse.builder()
                .id("61460cf94358")
                .username("user")
                .roles(Set.of(roleResponse))
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                .thenReturn(userResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("61460cf94358"));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        // GIVEN
        request.setUsername("sa");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1304))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("UserName is invalid!"));
    }
}
