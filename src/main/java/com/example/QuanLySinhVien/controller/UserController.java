package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse<?> createUser(@RequestBody UserRequest request) {
        return ApiResponse.builder()
                .result(userService.createUser(request))
                .build();
    }
}
