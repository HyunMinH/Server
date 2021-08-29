package com.example.bookreservationserver.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "COMMON_001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "COMMON_002", "Method not allowed"),
    HANDLE_ACCESS_DENIED(403, "COMMON_003", "Access is Denied"),

    // Standard
    ILLEGAL_STATE(400, "STANDARD_001", "illegal state"),
    ILLEGAL_ARGUMENT(400, "STANDARD_002", "illegal argument"),

    // Member
    EMAIL_DUPLICATION(400, "USER_001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "USER_002", "Login input is invalid"),

    // Exception
    EXCEPTION(500, "EXCEPTION", "exception");

    private int status;
    private final String code;
    private final String message;
}
