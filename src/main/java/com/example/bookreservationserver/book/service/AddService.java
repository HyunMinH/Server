package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.book.dto.AddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddService {
    private BookEntityRepository bookEntityRepository;

    public void addBook(AddRequest addRequest){
        Book book = new Book(addRequest);
        bookEntityRepository.save(book);
    }

    @Autowired
    public AddService(BookEntityRepository bookEntityRepository){
        this.bookEntityRepository = bookEntityRepository;
    }
}
