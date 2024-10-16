package com.task.library_management_system.controller;

import com.task.library_management_system.dto.UserDto;
import com.task.library_management_system.entity.User;
import com.task.library_management_system.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController extends CrudController<User, UserDto, UserService> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}

