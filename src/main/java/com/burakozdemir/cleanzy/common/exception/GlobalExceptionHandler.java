package com.burakozdemir.cleanzy.common.exception;

import com.burakozdemir.cleanzy.common.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
                .body(ApiErrorResponse.of(ErrorType.AUTHENTICATION_ERROR));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(ErrorType.UNAUTHORIZED_ACCESS.getHttpStatus())
                .body(ApiErrorResponse.of(ErrorType.UNAUTHORIZED_ACCESS));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(ErrorType.VALIDATION_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(ErrorType.VALIDATION_ERROR));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        return ResponseEntity
                .status(ErrorType.VALIDATION_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(ErrorType.VALIDATION_ERROR));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .status(ErrorType.VALIDATION_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(ErrorType.VALIDATION_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(ApiErrorResponse.of(ErrorType.AUTHENTICATION_ERROR));
    }
}
