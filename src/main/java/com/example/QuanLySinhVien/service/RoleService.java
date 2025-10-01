package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.RoleRequest;
import com.example.QuanLySinhVien.dto.response.RoleResponse;
import com.example.QuanLySinhVien.entity.Permission;
import com.example.QuanLySinhVien.entity.Role;
import com.example.QuanLySinhVien.mapper.RoleMapper;
import com.example.QuanLySinhVien.repository.PermissionRepository;
import com.example.QuanLySinhVien.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper RoleMapper;
    private final PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        var role = RoleMapper.toEntity(request);

        var permission = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permission));

        return RoleMapper.toDto(roleRepository.save(role));
    }

    public List<RoleResponse> getRoles(){
        return roleRepository.findAll().stream().map(RoleMapper::toDto).toList();
    }
    
    public void deleteRole(String RoleId){
        roleRepository.deleteById(RoleId);
    }
}
