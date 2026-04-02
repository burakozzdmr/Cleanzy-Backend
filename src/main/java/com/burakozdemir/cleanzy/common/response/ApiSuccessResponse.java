package com.burakozdemir.cleanzy.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "timestamp", "totalResults", "data"})
public class ApiSuccessResponse<T> {
    private final boolean success;
    private final Integer totalResults;
    private final LocalDateTime timestamp;
    private final T data;

    public static <T> ApiSuccessResponse<T> of(T data) {
        return ApiSuccessResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiSuccessResponse<T> of(T data, int totalResults) {
        return ApiSuccessResponse.<T>builder()
                .success(true)
                .data(data)
                .totalResults(totalResults)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
