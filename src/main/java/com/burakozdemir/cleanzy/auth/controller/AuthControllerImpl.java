package com.burakozdemir.cleanzy.auth.controller;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;
import com.burakozdemir.cleanzy.auth.service.AuthService;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiSuccessResponse.of(response));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiSuccessResponse.of(response));
    }
}
