package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.UserResponse;
import com.example.QuanLySinhVien.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequest request);

//    @Mapping(target = "roles", ignore = true)
    UserResponse toDto(User user);

    @Mapping(target = "roles", ignore = true)
    void update(@MappingTarget User user, UserRequest request);
}
