package com.burakozdemir.cleanzy.auth.controller;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<ApiSuccessResponse<AuthResponse>> register(RegisterRequest request);

    ResponseEntity<ApiSuccessResponse<AuthResponse>> login(LoginRequest request);
}
