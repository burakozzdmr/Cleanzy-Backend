package com.burakozdemir.cleanzy.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    AUTHENTICATION_ERROR(
            HttpStatus.UNAUTHORIZED,
            "AUTHENTICATION_ERROR",
            "Authentication failed"
    ),

    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "USER_NOT_FOUND",
            "User not found"
    ),

    AUTHERIZATION_ERROR(
            HttpStatus.FORBIDDEN,
            "AUTHERIZATION_ERROR",
            "Autherization error."
    ),

    USER_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "USER_ALREADY_EXISTS",
            "this email is already exists."
    ),

    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR",
            "Validation error"
    );

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorType(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
