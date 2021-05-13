package com.example.bookreservationserver.advice;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorResponse {
    private String code;
    private String message;
    private int status;
    private List<CustomFieldError> errors;

    public static class CustomFieldError{
        private String field;
        private String value;
        private String reason;

        private CustomFieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() { return field; }

        public String getValue() { return value; }

        public String getReason() { return reason; }
    }

    public ErrorResponse(String code, String message, int status, List<FieldError> errors) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.errors = errors.stream()
                .map(fieldError -> new CustomFieldError(fieldError.getField(), fieldError.getRejectedValue().toString(),fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public ErrorResponse(String code, String message, int status, String exceptionMessage) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.errors = List.of(new CustomFieldError("", "", exceptionMessage));
    }

    public static ErrorResponse of(ErrorCode errorCode){
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errorCode.getStatus(), Collections.emptyList());
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult){
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errorCode.getStatus(), bindingResult.getFieldErrors());
    }

    public static ErrorResponse of(ErrorCode errorCode, String exceptionMessage){
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errorCode.getStatus(), exceptionMessage);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public List<CustomFieldError> getErrors() { return errors; }
}
