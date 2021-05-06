package com.example.bookreservationserver.borrow.domain.aggregate;


import javax.persistence.*;

@Embeddable
@Access(AccessType.FIELD)
public class BorrowLine {
    private Long bookId;

    protected BorrowLine(Long bookId) {
        this.bookId = bookId;
    }

    public BorrowLine() {

    }

    public Long getBookId() { return bookId; }
}
