package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.UserResponse;
import com.example.QuanLySinhVien.entity.User;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.UserMapper;
import com.example.QuanLySinhVien.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }
}
