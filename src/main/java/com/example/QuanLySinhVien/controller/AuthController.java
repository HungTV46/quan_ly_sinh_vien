package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.IntrospectRequest;
import com.example.QuanLySinhVien.dto.request.LogoutRequest;
import com.example.QuanLySinhVien.dto.request.LoginRequest;
import com.example.QuanLySinhVien.dto.request.RefreshTokenRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ApiResponse<?> authenticate (@RequestBody LoginRequest request) throws JOSEException {
        var result = authService.authenticate(request);
        return  ApiResponse.builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<?> introspect (@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        var result = authService.introspect(request);
        return  ApiResponse.builder()
                .result(result)
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<?> authenticate (@RequestBody RefreshTokenRequest request) throws JOSEException, ParseException {
        var result = authService.refreshToken(request);
        return  ApiResponse.builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout (@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authService.logout(request);
        return ApiResponse.builder()
                .result("You are logout successful!")
                .build();
    }

}
