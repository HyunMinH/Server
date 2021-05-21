package com.example.bookreservationserver.book.domain.aggregate;

import com.example.bookreservationserver.book.dto.AddRequest;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Access(AccessType.FIELD)
public class Book {
    private static final String base_image_url = "https://knu-moapp2.s3.ap-northeast-2.amazonaws.com/static/";

    @Id
    @GeneratedValue
    private Long book_id;

    private String book_name;

    private String author;

    private String library;

    private String publisher;

    private LocalDate publication_date;

    private String image_url;

    protected Book() {}

    public Book(AddRequest addRequest){
        this.book_name = addRequest.getBook_name();
        this.author = addRequest.getAuthor();
        this.library = addRequest.getLibrary();
        this.publisher = addRequest.getPublisher();
        this.publication_date = addRequest.getPublication_date();
        this.image_url = base_image_url + book_name;
    }

    public Book(Long book_id, String book_name, String author, String library, String publisher, LocalDate publication_date){
        this.book_id = book_id;
        this.book_name = book_name;
        this.author = author;
        this.library = library;
        this.publisher = publisher;
        this.publication_date = publication_date;
    }

    //getter
    public Long getBook_id() { return book_id; }
    public String getBook_name() { return book_name; }
    public String getAuthor() { return author; }
    public String getLibrary() { return library; }
    public String getPublisher() { return publisher; }
    public LocalDate getPublication_date() { return publication_date; }
    public String getImage_url() { return image_url; }
}
