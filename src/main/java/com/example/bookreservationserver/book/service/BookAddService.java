package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookAddService {
    private BookRepository bookRepository;

    public void addBook(BookRequestDto bookRequestDto){
        Book book = new Book(bookRequestDto);
        bookRepository.save(book);
    }

    @Autowired
    public BookAddService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
}
