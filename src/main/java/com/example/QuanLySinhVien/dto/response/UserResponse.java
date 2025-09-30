package com.example.QuanLySinhVien.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserResponse {
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    Set<String> roles;
}
