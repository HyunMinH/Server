package com.example.bookreservationserver.book.dto;

import com.example.bookreservationserver.book.domain.aggregate.Book;

import java.time.LocalDate;
import java.util.Locale;

public class BookResponse {
    private Long book_id;

    private String book_name;

    private String author;

    private String library;

    private String publisher;

    private LocalDate publication_date;

    private String image_url;

    private String status;

    public BookResponse(Book book, String status){
        this.book_id = book.getBook_id();
        this.book_name = book.getBook_name();
        this.author = book.getAuthor();
        this.library = book.getLibrary();
        this.publisher = book.getPublisher();
        this.publication_date = book.getPublication_date();
        this.image_url = book.getImage_url();
        this.status = status;
    }

    public Long getBook_id() {
        return book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor() {
        return author;
    }

    public String getLibrary() {
        return library;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getStatus() {
        return status;
    }
}
