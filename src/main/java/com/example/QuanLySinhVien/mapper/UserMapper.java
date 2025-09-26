package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.UserResponse;
import com.example.QuanLySinhVien.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest request);

    UserResponse toDto(User user);
}
