package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookRepository bookRepository;

    public BookDto infoBook(Long bookId){
        return  bookRepository.findBookDtoById(bookId)
                .orElseThrow(()->new IllegalArgumentException("해당되는 책이 없습니다."));
    }

    public List<BookDto> infoBooks() {
        return bookRepository.findBookDtoAll();
    }
}
