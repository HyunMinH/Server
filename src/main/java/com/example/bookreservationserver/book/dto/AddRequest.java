package com.example.bookreservationserver.book.dto;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@NoArgsConstructor
public class AddRequest {
    @NotBlank
    private String book_name;

    @NotBlank
    private String author;

    @NotBlank
    private String library;

    @NotBlank
    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate publication_date;

    public AddRequest(String book_name, String author, String library, String publisher, LocalDate publication_date){
        this.book_name = book_name;
        this.author = author;
        this.library = library;
        this.publisher = publisher;
        this.publication_date = publication_date;
    }

    public String getBook_name() { return book_name; }

    public String getAuthor() { return author; }

    public String getLibrary() { return library; }

    public String getPublisher() { return publisher; }

    public LocalDate getPublication_date() { return publication_date; }
}
