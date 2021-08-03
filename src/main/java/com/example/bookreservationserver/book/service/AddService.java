package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.AddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddService {
    private BookRepository bookRepository;

    public void addBook(AddRequest addRequest){
        Book book = new Book(addRequest);
        bookRepository.save(book);
    }

    @Autowired
    public AddService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
}
