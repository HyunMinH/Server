package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookDeleteService {
    private final BookRepository bookRepository;

    @Transactional
    public void deleteBook(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        // 삭제하는 기능??
        bookRepository.delete(book);
    }
}
