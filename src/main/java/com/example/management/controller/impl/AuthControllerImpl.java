package com.example.management.controller.impl;

import com.example.management.controller.AuthController;
import com.example.management.dto.JwtAuthenticationResponse;
import com.example.management.dto.SignInRequest;
import com.example.management.dto.SignUpRequest;
import com.example.management.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationService authenticationService;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
