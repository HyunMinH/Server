package com.example.bookreservationserver.borrow.dto;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowLine;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrower;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowResponse {

    private Long borrow_id;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private List<Long> booksIds;

    public BorrowResponse(Borrow borrow) {
        this.borrow_id = borrow.getBorrow_id();
        this.state = borrow.getState();
        this.createdAt = borrow.getCreatedAt();
        this.expiredAt = borrow.getExpiredAt();
        this.booksIds = borrow.getBorrowLines().stream().map(borrowLine -> borrowLine.getBookId()).collect(Collectors.toList());
    }

    public Long getBorrow_id() {
        return borrow_id;
    }

    public BorrowState getState() {
        return state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public List<Long> getBooksIds() {
        return booksIds;
    }
}
