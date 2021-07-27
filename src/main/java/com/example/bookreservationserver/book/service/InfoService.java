package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookResponse;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;

    public List<Book> infoBook(Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        return List.of(book.get());
    }

    public List<BookResponse> infoBooks() {
        List<Borrow> borrowings = borrowRepository.findAll().stream().filter((borrow)->(borrow.isBorrowing() || borrow.isExpired())).collect(Collectors.toList());
        Set<Long> borrowingsIdSet = borrowings.stream().map(Borrow::getBookId).collect(Collectors.toSet());

        List<Book> books = bookRepository.findAll();
        return books.stream().map( book -> {
                if(borrowingsIdSet.contains(book.getBook_id())) return new BookResponse(book, "BORROWING");
                else return new BookResponse(book, "CAN BORROW");
        }).collect(Collectors.toList());
    }
}
