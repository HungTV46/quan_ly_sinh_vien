package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.UserResponse;
import com.example.QuanLySinhVien.entity.User;
import com.example.QuanLySinhVien.enums.Roles;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.UserMapper;
import com.example.QuanLySinhVien.repository.RoleRepository;
import com.example.QuanLySinhVien.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        return userMapper.toDto(userRepository.save(user));
    }

//    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo(){
        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
//        userRepository.findByUsername(authenticatedUser.getName());
        return userMapper.toDto(userRepository.findByUsername(authenticatedUser.getName()).orElseThrow(()->new AppException(ErrorCode.USERNAME_NOT_FOUND)));
    }

    public UserResponse update(String id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_NOTFOUND, "UserId"));

        userMapper.update(user,request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var role = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(role));

        return userMapper.toDto(userRepository.save(user));
    }
}
