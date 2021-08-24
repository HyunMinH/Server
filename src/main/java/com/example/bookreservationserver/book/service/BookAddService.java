package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAddService {
    private final BookRepository bookRepository;

    public void addBook(BookRequestDto bookRequestDto){
        Book book = new Book(bookRequestDto);
        bookRepository.save(book);
    }
}
