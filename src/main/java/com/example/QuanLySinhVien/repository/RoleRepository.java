package com.example.QuanLySinhVien.repository;

import com.example.QuanLySinhVien.entity.Permission;
import com.example.QuanLySinhVien.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
