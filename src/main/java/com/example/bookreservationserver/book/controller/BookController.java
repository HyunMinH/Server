package com.example.bookreservationserver.book.controller;

import com.example.bookreservationserver.book.dto.BookDto;
import com.example.bookreservationserver.book.dto.BookRequestDto;
import com.example.bookreservationserver.book.service.BookAddService;
import com.example.bookreservationserver.book.service.BookDeleteService;
import com.example.bookreservationserver.book.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookAddService bookAddService;
    private final BookDeleteService bookDeleteService;
    private final BookSearchService bookSearchService;

    @PostMapping(value = "/api/book", produces = "application/json; charset=utf8")
    public void addBook(@RequestBody @Valid BookRequestDto bookRequestDto) {
        bookAddService.addBook(bookRequestDto);
    }

    @DeleteMapping("/api/book/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookDeleteService.deleteBook(bookId);
    }

    @GetMapping("/api/book")
    public List<BookDto> infoBooks(){
        return  bookSearchService.infoBooks();
    }

    @GetMapping("/api/book/{bookId}")
    public BookDto infoBook(@PathVariable("bookId") Long bookId){
        return bookSearchService.infoBook(bookId);
    }
}
