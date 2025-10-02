package com.example.QuanLySinhVien.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
}
