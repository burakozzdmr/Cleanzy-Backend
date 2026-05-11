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

    AUTHORIZATION_ERROR(
            HttpStatus.FORBIDDEN,
            "AUTHORIZATION_ERROR",
            "Autherization error."
    ),

    USER_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "USER_ALREADY_EXISTS",
            "this email is already exists."
    ),

    CUSTOMER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "CUSTOMER_NOT_FOUND",
            "Customer not found"
    ),

    CLEANER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "CLEANER_NOT_FOUND",
            "Cleaner not found"
    ),

    JOB_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "JOB_NOT_FOUND",
            "Job not found"
    ),

    FAVORITE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "FAVORITE_NOT_FOUND",
            "Favorite not found"
    ),

    ALREADY_FAVORITED(
            HttpStatus.CONFLICT,
            "ALREADY_FAVORITED",
            "This user is already in your favorites"
    ),

    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR",
            "Validation error"
    ),

    INVALID_ROLE(
            HttpStatus.BAD_REQUEST,
            "INVALID_ROLE",
            "Invalid role specified"
    ),

    UNAUTHORIZED_ACCESS(
            HttpStatus.FORBIDDEN,
            "UNAUTHORIZED_ACCESS",
            "You are not authorized to perform this action"
    ),

    TOKEN_BLACKLISTED(
            HttpStatus.UNAUTHORIZED,
            "TOKEN_BLACKLISTED",
            "Token has been invalidated"
    ),

    INVALID_PASSWORD(
            HttpStatus.BAD_REQUEST,
            "INVALID_PASSWORD",
            "Current password is incorrect"
    ),

    JOB_CANNOT_BE_DELETED(
            HttpStatus.CONFLICT,
            "JOB_CANNOT_BE_DELETED",
            "Only jobs with status OPEN or CANCELLED can be deleted"
    ),

    REVIEW_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "REVIEW_NOT_FOUND",
            "Review not found"
    ),

    REVIEW_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "REVIEW_ALREADY_EXISTS",
            "You have already reviewed this job"
    ),

    JOB_NOT_COMPLETED(
            HttpStatus.BAD_REQUEST,
            "JOB_NOT_COMPLETED",
            "Reviews can only be created for completed jobs"
    ),

    CONVERSATION_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "CONVERSATION_NOT_FOUND",
            "Conversation not found"
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
