package com.burakozdemir.cleanzy.auth.controller;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.ChangePasswordRequest;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RefreshTokenRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<ApiSuccessResponse<AuthResponse>> register(RegisterRequest request);

    ResponseEntity<ApiSuccessResponse<AuthResponse>> login(LoginRequest request);

    ResponseEntity<ApiSuccessResponse<Boolean>> logout(HttpServletRequest request);

    ResponseEntity<ApiSuccessResponse<AuthResponse>> refresh(RefreshTokenRequest request);

    ResponseEntity<ApiSuccessResponse<Boolean>> changePassword(
            HttpServletRequest request,
            ChangePasswordRequest body
    );
}
