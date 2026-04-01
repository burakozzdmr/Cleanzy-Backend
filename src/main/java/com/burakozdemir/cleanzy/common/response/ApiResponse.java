package com.burakozdemir.cleanzy.common.response;


import com.burakozdemir.cleanzy.common.exception.ErrorDetails;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private Integer totalResults;
    private LocalDateTime timestamp;
    private T data;
    private ErrorDetails errorDetails;
}
