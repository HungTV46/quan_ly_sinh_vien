package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.PermissionRequest;
import com.example.QuanLySinhVien.dto.response.PermissionResponse;
import com.example.QuanLySinhVien.entity.Permission;
import com.example.QuanLySinhVien.mapper.PermissionMapper;
import com.example.QuanLySinhVien.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toEntity(request);

        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getPermissions(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toDto).toList();
    }

    public void deletePermission(String permissionId){
        permissionRepository.deleteById(permissionId);
    }
}
