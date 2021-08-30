package com.example.bookreservationserver.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BookRequestDto {
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
}
