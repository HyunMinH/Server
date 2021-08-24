package com.example.bookreservationserver.book.dto;

import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class BookDto {
    private Long id;

    private String book_name;

    private String author;

    private String library;

    private String publisher;

    private LocalDate publication_date;

    private String image_url;

    private BorrowState state;
}
