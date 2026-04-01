package com.burakozdemir.cleanzy.auth.service;

import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;

    AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
}
