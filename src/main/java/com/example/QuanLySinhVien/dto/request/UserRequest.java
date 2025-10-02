package com.example.QuanLySinhVien.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;

    private String password;
    private List<String> roles;
}
