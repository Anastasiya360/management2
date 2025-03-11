package com.example.management.service.impl;

import com.example.management.entity.User;
import com.example.management.enums.Role;
import com.example.management.exceptoin.ApiException;
import com.example.management.repository.UserRepository;
import com.example.management.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        user.setId(null);
        if (!EnumUtils.isValidEnum(Role.class, user.getRole())) {
            throw new ApiException("Роль задана не верно", HttpServletResponse.SC_BAD_REQUEST);
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new ApiException("Имя не передано", HttpServletResponse.SC_BAD_REQUEST);
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().length() < 5) {
            throw new ApiException("Email не передан или имеет менее 5 символов", HttpServletResponse.SC_BAD_REQUEST);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ApiException("Email уже существует", HttpServletResponse.SC_BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().length() < 8) {
            throw new ApiException("Пароль не передан или имеет менее 8 символов", HttpServletResponse.SC_BAD_REQUEST);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Integer userId) {
        if (userId == null) {
            throw new ApiException("Идентификатор пользователя не задан", HttpServletResponse.SC_BAD_REQUEST);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ApiException("Пользователь не найдена", HttpServletResponse.SC_NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public User getByUserEmail(String email) {
        if (userRepository.findUserByEmail(email) != null) {
            return userRepository.findUserByEmail(email);
        } else {
            throw new ApiException("Пользователь не найден", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUserEmail;
    }
}
