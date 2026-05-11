package com.burakozdemir.cleanzy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Email
    @Schema(example = "user@example.com")
    private String email;

    @NotBlank
    @Schema(example = "secret123")
    private String password;

    @Schema(
            description = "Optional. If provided, must match the role the account was registered with.",
            example = "CUSTOMER",
            allowableValues = {"CUSTOMER", "CLEANER"}
    )
    private String role;
}
