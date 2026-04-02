package com.burakozdemir.cleanzy.common.response;

import com.burakozdemir.cleanzy.common.exception.ErrorDetails;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "timestamp", "errorDetails"})
public class ApiErrorResponse {
    private final boolean success;
    private final LocalDateTime timestamp;
    private final ErrorDetails errorDetails;

    public static ApiErrorResponse of(ErrorType errorType) {
        return ApiErrorResponse.builder()
                .success(false)
                .timestamp(LocalDateTime.now())
                .errorDetails(ErrorDetails.builder()
                        .errorCode(errorType.getErrorCode())
                        .errorMessage(errorType.getErrorMessage())
                        .build())
                .build();
    }
}
