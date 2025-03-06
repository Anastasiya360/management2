package com.example.management.service;

import com.example.management.dto.JwtAuthenticationResponse;
import com.example.management.dto.SignInRequest;
import com.example.management.dto.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    JwtAuthenticationResponse signUp(SignUpRequest request);

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    JwtAuthenticationResponse signIn(SignInRequest request);
}
