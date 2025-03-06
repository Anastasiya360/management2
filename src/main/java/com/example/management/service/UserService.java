package com.example.management.service;

import com.example.management.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    User create(User user);

    /**
     * Получение пользователя по id
     *
     * @return пользователь
     */
    User findById(Integer userId);

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    User getByUserEmail(String email);

    /**
     * Получение пользователя по email для Spring Security
     *
     * @return пользователь
     */
    UserDetailsService userDetailsService();
}
