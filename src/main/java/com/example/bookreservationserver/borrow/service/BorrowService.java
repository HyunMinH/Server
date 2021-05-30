package com.example.bookreservationserver.borrow.service;


import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.BorrowResponse;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BorrowService {
    private BorrowEntityRepository borrowEntityRepository;
    private BookEntityRepository bookEntityRepository;
    private UserEntityRepository userEntityRepository;

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest borrowRequest){
        if(!userEntityRepository.existsById(borrowRequest.getBorrowerId()))
            throw new IllegalArgumentException("빌리려는 사용자의 id가 맞지 않습니다.");

        List<Borrow> borrows = borrowEntityRepository.findBorrowsByBookId(borrowRequest.getBookId());
        long borrow_count = borrows.stream().filter(b -> b.isExpired() || b.isBorrowing()).count();
        if(borrow_count > 0) {
            throw new IllegalArgumentException("해당 책은 이미 대여중인 책입니다.");
        }

        if(!bookEntityRepository.existsById(borrowRequest.getBookId())){
            throw new IllegalArgumentException("해당 id를 가지는 책이 없습니다. (" + borrowRequest.getBookId() + ")");
        }

        Borrow borrow = new Borrow(borrowRequest);
        borrowEntityRepository.save(borrow);

        Book book = bookEntityRepository.findById(borrowRequest.getBookId()).get();

        return new BorrowResponse(borrow, book);
    }


    @Autowired
    public BorrowService(BorrowEntityRepository borrowEntityRepository, BookEntityRepository bookEntityRepository, UserEntityRepository userEntityRepository) {
        this.borrowEntityRepository = borrowEntityRepository;
        this.bookEntityRepository = bookEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }
}
