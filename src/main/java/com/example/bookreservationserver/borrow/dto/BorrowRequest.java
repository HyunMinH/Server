package com.example.bookreservationserver.borrow.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BorrowRequest {

    @NotNull
    private Long borrowerId;

    @NotBlank
    private String borrowerName;

    @NotNull
    private List<Long> bookIds;

    public Long getBorrowerId() {
        return borrowerId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }
}
