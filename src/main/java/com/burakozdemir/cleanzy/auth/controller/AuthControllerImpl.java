package com.burakozdemir.cleanzy.auth.controller;

import com.burakozdemir.cleanzy.auth.service.AuthService;
import com.burakozdemir.cleanzy.auth.service.AuthServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/v1/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    
}
