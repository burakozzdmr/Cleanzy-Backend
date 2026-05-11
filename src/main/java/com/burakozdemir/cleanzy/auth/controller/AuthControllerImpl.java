package com.burakozdemir.cleanzy.auth.controller;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.ChangePasswordRequest;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RefreshTokenRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;
import com.burakozdemir.cleanzy.auth.service.AuthService;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/register")
    @Operation(summary = "Register a new user (CUSTOMER or CLEANER)")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiSuccessResponse.of(response));
    }

    @Override
    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiSuccessResponse.of(response));
    }

    @Override
    @PostMapping("/logout")
    @Operation(summary = "Logout — invalidate current JWT token", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiSuccessResponse<Boolean>> logout(HttpServletRequest request) {
        String token = extractToken(request);
        authService.logout(token);
        return ResponseEntity.ok(ApiSuccessResponse.of(true));
    }

    @Override
    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiSuccessResponse.of(response));
    }

    @Override
    @PatchMapping("/change-password")
    @Operation(summary = "Change password", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiSuccessResponse<Boolean>> changePassword(
            HttpServletRequest request,
            @Valid @RequestBody ChangePasswordRequest body
    ) {
        String token = extractToken(request);
        authService.changePassword(token, body);
        return ResponseEntity.ok(ApiSuccessResponse.of(true));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorType.AUTHENTICATION_ERROR);
        }
        return authHeader.substring(7);
    }
}
