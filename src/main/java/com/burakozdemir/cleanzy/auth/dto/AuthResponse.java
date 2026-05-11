package com.burakozdemir.cleanzy.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;
    private Long userId;
    private String role;
    private String fullName;
    private String email;
}
