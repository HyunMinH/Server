package com.example.bookreservationserver.borrow.dto;

public class ReturnResponse {
    private String result;

    public ReturnResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
