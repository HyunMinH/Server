package com.example.bookreservationserver.advice;

public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "COMMON_001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "COMMON_002", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "COMMON_003", "Access is Denied"),

    // Standard
    ILLEGAL_STATE(500, "STANDARD_001", "illegal state"),
    ILLEGAL_ARGUMENT(501, "STANDARD_002", "illegal argument"),

    // Member
    EMAIL_DUPLICATION(400, "USER_001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "USER_002", "Login input is invalid");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    protected String getCode() {
        return code;
    }

    protected String getMessage() {
        return message;
    }

    protected int getStatus() {
        return status;
    }
}
