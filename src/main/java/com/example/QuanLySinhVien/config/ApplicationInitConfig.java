package com.example.QuanLySinhVien.config;

import com.example.QuanLySinhVien.entity.User;
import com.example.QuanLySinhVien.enums.Roles;
import com.example.QuanLySinhVien.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner initApplicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                var role = new HashSet<String>();
                role.add(Roles.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(role)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change password");
            }
        };
    }
}
