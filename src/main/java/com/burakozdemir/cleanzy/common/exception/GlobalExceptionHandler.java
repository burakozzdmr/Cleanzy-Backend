package com.burakozdemir.cleanzy.common.exception;

import com.burakozdemir.cleanzy.common.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorType errorType = ex.getErrorType();
        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ApiErrorResponse.of(errorType));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(ErrorType.AUTHENTICATION_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(ErrorType.AUTHERIZATION_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(ErrorType.VALIDATION_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(ErrorType.VALIDATION_ERROR));
    }
}
