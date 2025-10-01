package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.PermissionRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permission")
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    ApiResponse<?> createPermission(@RequestBody PermissionRequest permissionRequest){
        return ApiResponse.builder()
                .result(permissionService.create(permissionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<?> getPermissions (){
        return ApiResponse.builder()
                .result(permissionService.getPermissions())
                .build();
    }

    @DeleteMapping("{name}")
    ApiResponse<?> deletePermission(@PathVariable @Valid String name){
        permissionService.deletePermission(name);
        return ApiResponse.builder()
                .result("Permission deleted")
                .build();
    }
}
