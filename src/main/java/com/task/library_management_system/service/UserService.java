package com.task.library_management_system.service;

import com.task.library_management_system.entity.User;
import com.task.library_management_system.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, UserRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("Username '[%s]' not found", username)));
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }
}
