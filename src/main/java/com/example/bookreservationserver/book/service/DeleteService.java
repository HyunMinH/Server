package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DeleteService {
    private BookEntityRepository bookEntityRepository;

    @Transactional
    public void deleteBook(Long bookId){
        Optional<Book> bookOptional = bookEntityRepository.findById(bookId);
        if(bookOptional.isEmpty()) throw new IllegalArgumentException("cannot found book by bookId[" +bookId + "]");
        // 삭제하는 기능??
        bookEntityRepository.delete(bookOptional.get());
    }

    @Autowired
    public DeleteService(BookEntityRepository bookEntityRepository){
        this.bookEntityRepository = bookEntityRepository;
    }
}
