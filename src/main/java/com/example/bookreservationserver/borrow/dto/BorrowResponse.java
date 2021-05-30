package com.example.bookreservationserver.borrow.dto;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BorrowResponse {

    private Long borrow_id;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    private LocalDate createdAt;

    private LocalDate expiredAt;

    private Long user_id;

    private String user_name;

    private Book book;

    public BorrowResponse(Borrow borrow, Book book) {
        this.borrow_id = borrow.getBorrow_id();
        this.state = borrow.getState();
        this.createdAt = borrow.getCreatedAt();
        this.expiredAt = borrow.getExpiredAt();

        this.user_id = borrow.getBorrower().getUserId();
        this.user_name = borrow.getBorrower().getUserName();

        this.book = book;
    }

    public Long getBorrow_id() { return borrow_id; }
    public BorrowState getState() { return state; }
    public LocalDate getCreatedAt() { return createdAt; }
    public LocalDate getExpiredAt() { return expiredAt; }
    public Book getBook() {
        return book;
    }
    public Long getUser_id() { return user_id; }
    public String getUser_name() { return user_name; }
}
