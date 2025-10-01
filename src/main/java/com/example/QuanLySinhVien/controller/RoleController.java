package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.dto.request.RoleRequest;
import com.example.QuanLySinhVien.dto.response.ApiResponse;
import com.example.QuanLySinhVien.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    ApiResponse<?> createRole(@RequestBody RoleRequest roleRequest){
        return ApiResponse.builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<?> getRoles (){
        return ApiResponse.builder()
                .result(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<?> deleteRole(@PathVariable @Valid String name){
        roleService.deleteRole(name);
        return ApiResponse.builder()
                .result("Role deleted")
                .build();
    }
}
