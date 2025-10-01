package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/my-info")
    public ApiResponse<?> getMyInfo() {
        return  ApiResponse.builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateUser(@PathVariable String id, @RequestBody @Valid UserRequest request) {
        return ApiResponse.builder()
                .result(userService.update(id, request))
                .build();
    }
}
