package com.example.QuanLySinhVien.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthResponse{
    private String accessToken;
    private Boolean checkInvalid;
}
