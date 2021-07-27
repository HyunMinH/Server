package com.example.bookreservationserver.borrow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowRequest {

    @NotNull
    private Long borrowerId;

    @NotBlank
    private String borrowerName;

    @NotNull
    private Long bookId;


    public Long getBorrowerId() { return borrowerId; }
    public String getBorrowerName() { return borrowerName; }
    public Long getBookId() { return bookId; }
}
