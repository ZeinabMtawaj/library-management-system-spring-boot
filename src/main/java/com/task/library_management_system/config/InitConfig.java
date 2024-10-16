package com.task.library_management_system.config;

import com.task.library_management_system.entity.User;
import com.task.library_management_system.entity.UserType;
import com.task.library_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(3)
public class InitConfig implements CommandLineRunner {

    private final UserService usersService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(final String... args) {
        initUser("admin", "admin", UserType.ADMIN);
        initUser("librarian", "librarian", UserType.LIBRARIAN);
    }

    private void initUser(String username, String password, UserType userType) {
        var admin = usersService.getByUsername(username).orElseGet(() -> usersService.save(User.builder()
                .username(username)
                .password(password)
                .type(userType).build()));
        log.info("User Initialized With Name: {}, Type: {}", admin.getUsername(), userType);
    }

}
