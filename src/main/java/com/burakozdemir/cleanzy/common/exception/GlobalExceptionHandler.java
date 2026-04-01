package com.burakozdemir.cleanzy.common.exception;

import com.burakozdemir.cleanzy.common.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse<ErrorDetails>> handleBusinessException(BusinessException ex) {
        return null;
    }
}
