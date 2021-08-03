package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookDto;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;

    public BookDto infoBook(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()->new IllegalArgumentException("해당되는 책이 없습니다."));
        return new BookDto(book);
    }

    public List<BookDto> infoBooks() {
        List<Borrow> borrowings = borrowRepository.findAll().stream().filter((borrow)->(borrow.isBorrowing() || borrow.isExpired())).collect(Collectors.toList());
        Set<Long> borrowingsIdSet = borrowings.stream().map(Borrow::getBookId).collect(Collectors.toSet());

        List<Book> books = bookRepository.findAll();
        return books.stream().map( book -> {
                if(borrowingsIdSet.contains(book.getBook_id())) return new BookDto(book, "BORROWING");
                else return new BookDto(book, "CAN BORROW");
        }).collect(Collectors.toList());
    }
}
