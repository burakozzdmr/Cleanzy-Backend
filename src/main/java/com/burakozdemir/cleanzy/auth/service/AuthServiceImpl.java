package com.burakozdemir.cleanzy.auth.service;

import com.burakozdemir.cleanzy.auth.dto.AuthResponse;
import com.burakozdemir.cleanzy.auth.dto.ChangePasswordRequest;
import com.burakozdemir.cleanzy.auth.dto.LoginRequest;
import com.burakozdemir.cleanzy.auth.dto.RefreshTokenRequest;
import com.burakozdemir.cleanzy.auth.dto.RegisterRequest;
import com.burakozdemir.cleanzy.auth.entity.Role;
import com.burakozdemir.cleanzy.auth.entity.TokenBlacklist;
import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.auth.repository.TokenBlacklistRepository;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final CustomerRepository customerRepository;
    private final CleanerRepository cleanerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorType.USER_ALREADY_EXISTS);
        }

        Role role = request.getRole();
        if (role == null) {
            throw new BusinessException(ErrorType.INVALID_ROLE);
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        User savedUser = authRepository.save(user);

        if (role == Role.CUSTOMER) {
            Customer customer = new Customer();
            customer.setUser(savedUser);
            customerRepository.save(customer);
        } else if (role == Role.CLEANER) {
            Cleaner cleaner = new Cleaner();
            cleaner.setUser(savedUser);
            cleaner.setRating(0.0);
            cleaner.setTotalReviews(0);
            cleaner.setAvailable(true);
            cleanerRepository.save(cleaner);
        }

        String token = jwtService.generateToken(savedUser);

        return buildAuthResponse(token, savedUser);
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
                .orElseThrow(() -> new BusinessException(ErrorType.AUTHORIZATION_ERROR));

        if (request.getRole() != null) {
            try {
                Role requestedRole = Role.valueOf(request.getRole().toUpperCase());
                if (!user.getRole().equals(requestedRole)) {
                    throw new BusinessException(ErrorType.ROLE_MISMATCH);
                }
            } catch (IllegalArgumentException e) {
                throw new BusinessException(ErrorType.INVALID_ROLE);
            }
        }

        String token = jwtService.generateToken(user);

        return buildAuthResponse(token, user);
    }

    @Override
    @Transactional
    public void logout(String token) {
        Date expiration = jwtService.extractExpiration(token);
        LocalDateTime expiresAt = expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        TokenBlacklist blacklisted = TokenBlacklist.builder()
                .token(token)
                .expiresAt(expiresAt)
                .build();

        tokenBlacklistRepository.save(blacklisted);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getToken();

        if (tokenBlacklistRepository.existsByToken(token)) {
            throw new BusinessException(ErrorType.TOKEN_BLACKLISTED);
        }

        String email = jwtService.extractUsername(token);
        User user = authRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        if (!jwtService.isTokenValid(token, user)) {
            throw new BusinessException(ErrorType.AUTHENTICATION_ERROR);
        }

        String newToken = jwtService.generateToken(user);
        return buildAuthResponse(newToken, user);
    }

    @Override
    @Transactional
    public void changePassword(String token, ChangePasswordRequest request) {
        String email = jwtService.extractUsername(token);
        User user = authRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException(ErrorType.INVALID_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        authRepository.save(user);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private AuthResponse buildAuthResponse(String token, User user) {
        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }
}
