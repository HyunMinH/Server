package com.example.bookreservationserver.book.domain.aggregate;

import com.example.bookreservationserver.book.dto.BookRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Access(AccessType.FIELD)
@AllArgsConstructor
@Getter
@Builder
public class Book {
    private static final String base_image_url = "https://knu-moapp2.s3.ap-northeast-2.amazonaws.com/static/";

    @Id
    @GeneratedValue
    private Long id;

    private String book_name;

    private String author;

    private String library;

    private String publisher;

    private LocalDate publication_date;

    private String image_url;

    protected Book() {}

    public Book(BookRequestDto bookRequestDto){
        this.book_name = bookRequestDto.getBook_name();
        this.author = bookRequestDto.getAuthor();
        this.library = bookRequestDto.getLibrary();
        this.publisher = bookRequestDto.getPublisher();
        this.publication_date = bookRequestDto.getPublication_date();
        this.image_url = base_image_url + book_name;
    }
}
