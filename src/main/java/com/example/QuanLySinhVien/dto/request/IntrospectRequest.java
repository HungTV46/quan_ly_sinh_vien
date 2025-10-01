package com.example.QuanLySinhVien.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectRequest {
    private String token;
}
