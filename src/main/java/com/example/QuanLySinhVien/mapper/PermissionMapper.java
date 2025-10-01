package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.PermissionRequest;
import com.example.QuanLySinhVien.dto.response.PermissionResponse;
import com.example.QuanLySinhVien.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toEntity(PermissionRequest request);

    PermissionResponse toDto(Permission entity);

    List<PermissionResponse> toListDto(List<Permission> entity);
}
