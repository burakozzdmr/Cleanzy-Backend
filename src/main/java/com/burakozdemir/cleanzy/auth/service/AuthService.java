package com.burakozdemir.cleanzy.auth.service;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.ChangePasswordRequest;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RefreshTokenRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String token);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void changePassword(String token, ChangePasswordRequest request);
}
