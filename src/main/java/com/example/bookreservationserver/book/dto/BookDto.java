package com.example.bookreservationserver.book.dto;

import com.example.bookreservationserver.book.domain.aggregate.Book;
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

    public BookDto(Book book, BorrowState state){
        this.id = book.getId();
        this.book_name = book.getBook_name();
        this.author = book.getAuthor();
        this.library = book.getLibrary();
        this.publisher = book.getPublisher();
        this.publication_date = book.getPublication_date();
        this.image_url = book.getImage_url();
        this.state = state;
    }


}
