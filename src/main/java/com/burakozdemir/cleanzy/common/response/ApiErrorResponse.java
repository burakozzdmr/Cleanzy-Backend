package com.burakozdemir.cleanzy.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<T> {
    private boolean success;
    private LocalDateTime timestamp;
    private T errorDetails;
}
