package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfoService {
    private BookEntityRepository bookEntityRepository;

    public List<Book> infoBook(Long bookId){
        Optional<Book> book = bookEntityRepository.findById(bookId);
        return List.of(book.get());
    }

    public List<Book> infoBooks() {
        return bookEntityRepository.findAll();
    }

    @Autowired
    public InfoService(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }
}
