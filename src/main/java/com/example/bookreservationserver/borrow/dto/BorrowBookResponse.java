package com.example.bookreservationserver.borrow.dto;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class BorrowBookResponse {
    private Long borrowId;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    private LocalDate createdAt;

    private LocalDate expiredAt;

    private Long userId;

    private String userName;

    private Book book;
}
