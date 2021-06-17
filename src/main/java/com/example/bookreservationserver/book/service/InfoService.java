package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.book.dto.BookResponse;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final BookEntityRepository bookEntityRepository;
    private final BorrowEntityRepository borrowEntityRepository;

    public List<Book> infoBook(Long bookId){
        Optional<Book> book = bookEntityRepository.findById(bookId);
        return List.of(book.get());
    }

    public List<BookResponse> infoBooks() {
        List<Borrow> borrowings = borrowEntityRepository.findAll().stream().filter((borrow)->(borrow.isBorrowing() || borrow.isExpired())).collect(Collectors.toList());
        Set<Long> borrowingsIdSet = borrowings.stream().map(Borrow::getBookId).collect(Collectors.toSet());

        List<Book> books = bookEntityRepository.findAll();
        return books.stream().map( book -> {
                if(borrowingsIdSet.contains(book.getBook_id())) return new BookResponse(book, "BORROWING");
                else return new BookResponse(book, "CAN BORROW");
        }).collect(Collectors.toList());
    }
}
