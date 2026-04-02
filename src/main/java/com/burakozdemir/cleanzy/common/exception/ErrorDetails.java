package com.burakozdemir.cleanzy.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
}
