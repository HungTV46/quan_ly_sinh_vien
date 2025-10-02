package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.UserRequest;
import com.example.QuanLySinhVien.dto.response.PermissionResponse;
import com.example.QuanLySinhVien.dto.response.RoleResponse;
import com.example.QuanLySinhVien.dto.response.UserResponse;
import com.example.QuanLySinhVien.entity.User;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;
    private UserRequest request;
    private UserResponse userResponse;
    private RoleResponse roleResponse;

    @BeforeEach
    void initData() {
        request = UserRequest.builder()
                .username("user")
                .password("user")
                .roles(List.of("USER"))
                .build();

        roleResponse = RoleResponse.builder()
                .name("USER")
                .description("role user")
                .permissions(Set.of(
                        PermissionResponse.builder().name("APPROVE_POST").description("approve post data permission").build(),
                        PermissionResponse.builder().name("CREATE_DATA").description("Create data permission").build()
                ))
                .build();

        userResponse = UserResponse.builder()
                .id("61460cf94358")
                .username("user")
                .roles(Set.of(roleResponse))
                .build();

        user = User.builder()
                .id("61460cf94358")
                .username("user")
//                .roles(Set.of(roleResponse))
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        // GIVEN
        when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        // WHEN
        var userResponse1 = userService.createUser(request);

        // THEN
        Assertions.assertThat(userResponse1.getId()).isEqualTo("61460cf94358");
        Assertions.assertThat(userResponse1.getUsername()).isEqualTo("user");
    }

    @Test
    void createUser_usernameExisted_fail(){
        // GIVEN
        when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1303);
        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("UserName existed!");
    }
}
