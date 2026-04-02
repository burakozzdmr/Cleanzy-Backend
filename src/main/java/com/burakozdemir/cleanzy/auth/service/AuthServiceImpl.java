package com.burakozdemir.cleanzy.auth.service;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;
import com.burakozdemir.cleanzy.auth.entity.Role;
import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorType.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        authRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorType.AUTHERIZATION_ERROR));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .build();
    }
}
