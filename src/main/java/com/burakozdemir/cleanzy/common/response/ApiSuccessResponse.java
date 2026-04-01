package com.burakozdemir.cleanzy.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccessResponse<T> {
    private boolean success;
    private Integer totalResults;
    private LocalDateTime timestamp;
    private T data;
}
