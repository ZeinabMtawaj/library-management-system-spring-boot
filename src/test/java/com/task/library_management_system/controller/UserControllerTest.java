package com.task.library_management_system.controller;

import com.task.library_management_system.dto.UserDto;
import com.task.library_management_system.entity.User;
import com.task.library_management_system.entity.UserType;
import com.task.library_management_system.service.UserService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(UserController.class)
public class UserControllerTest extends CrudControllerTest<User, UserDto, UserService> {

    @Override
    protected User createEntity() {
        return User.builder()
                .id(1L)
                .username("Test User")
                .type(UserType.ADMIN)
                .build();
    }

    @Override
    protected UserDto createDto() {
        return UserDto.builder()
                .id(1L)
                .username("Test User")
                .password("password")
                .type(UserType.ADMIN)
                .build();
    }

    @Override
    protected String getEntityEndpoint() {
        return "/api/users";
    }

}