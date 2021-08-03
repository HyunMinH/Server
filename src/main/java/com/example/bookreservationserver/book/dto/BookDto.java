package com.example.bookreservationserver.book.dto;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class BookDto {
    private Long book_id;

    private String book_name;

    private String author;

    private String library;

    private String publisher;

    private LocalDate publication_date;

    private String image_url;

    private String status;

    public BookDto(Book book, String status){
        this.book_id = book.getBook_id();
        this.book_name = book.getBook_name();
        this.author = book.getAuthor();
        this.library = book.getLibrary();
        this.publisher = book.getPublisher();
        this.publication_date = book.getPublication_date();
        this.image_url = book.getImage_url();
        this.status = status;
    }

    public BookDto(Book book){
        this.book_id = book.getBook_id();
        this.book_name = book.getBook_name();
        this.author = book.getAuthor();
        this.library = book.getLibrary();
        this.publisher = book.getPublisher();
        this.publication_date = book.getPublication_date();
        this.image_url = book.getImage_url();
    }
}
