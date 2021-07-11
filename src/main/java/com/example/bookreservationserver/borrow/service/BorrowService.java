package com.example.bookreservationserver.borrow.service;


import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.domain.service.BorrowValidator;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.BorrowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowEntityRepository borrowEntityRepository;
    private final BookEntityRepository bookEntityRepository;
    private final BorrowValidator borrowValidator;

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest borrowRequest){
        Borrow borrow = Borrow.createBorrow(borrowRequest, borrowValidator);
        borrowEntityRepository.save(borrow);

        Book book = bookEntityRepository.findById(borrowRequest.getBookId()).get();
        return new BorrowResponse(borrow, book);
    }
}