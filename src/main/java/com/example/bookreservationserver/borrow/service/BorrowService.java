package com.example.bookreservationserver.borrow.service;


import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import com.example.bookreservationserver.borrow.domain.service.BorrowValidator;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowService {
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final BorrowValidator borrowValidator;

    @Transactional
    public Borrow borrowBook(BorrowRequest borrowRequest){
        Borrow borrow = Borrow.createBorrow(borrowRequest, borrowValidator);
        borrowRepository.save(borrow);
        return borrow;
    }
}