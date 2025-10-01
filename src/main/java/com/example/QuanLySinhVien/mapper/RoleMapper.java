package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.RoleRequest;
import com.example.QuanLySinhVien.dto.response.RoleResponse;
import com.example.QuanLySinhVien.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleRequest request);

    RoleResponse toDto(Role entity);

    List<RoleResponse> toListDto(List<RoleResponse> entity);
}
