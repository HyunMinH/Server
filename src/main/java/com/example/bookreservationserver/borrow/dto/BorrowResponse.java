package com.example.bookreservationserver.borrow.dto;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;

import javax.persistence.*;
import java.time.LocalDateTime;

public class BorrowResponse {

    private Long borrow_id;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private Book book;

    public BorrowResponse(Borrow borrow, Book book) {
        this.borrow_id = borrow.getBorrow_id();
        this.state = borrow.getState();
        this.createdAt = borrow.getCreatedAt();
        this.expiredAt = borrow.getExpiredAt();
        this.book = book;
    }

    public Long getBorrow_id() { return borrow_id; }
    public BorrowState getState() { return state; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiredAt() { return expiredAt; }

    public Book getBook() {
        return book;
    }
}
