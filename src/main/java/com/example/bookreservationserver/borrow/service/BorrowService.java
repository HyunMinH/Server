package com.example.bookreservationserver.borrow.service;


import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.BorrowResponse;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowEntityRepository borrowEntityRepository;
    private final BookEntityRepository bookEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest borrowRequest){
        validate(borrowRequest);

        Borrow borrow = new Borrow(borrowRequest);
        borrowEntityRepository.save(borrow);

        Book book = bookEntityRepository.findById(borrowRequest.getBookId()).get();
        return new BorrowResponse(borrow, book);
    }

    private void validate(BorrowRequest borrowRequest){
        if(!userEntityRepository.existsById(borrowRequest.getBorrowerId()))
            throw new IllegalArgumentException("빌리려는 사용자의 id가 맞지 않습니다.");

        List<Borrow> usersBorrows = borrowEntityRepository.findBorrowsByBorrower_UserId(borrowRequest.getBorrowerId());
        if(usersBorrows.stream().anyMatch(Borrow::isExpired)){
            throw new IllegalStateException("연체된 책이 있습니다.");
        }

        if(usersBorrows.size() >= 3){
            throw new IllegalStateException("3권 이상 빌릴 수 없습니다");
        }

        if(!bookEntityRepository.existsById(borrowRequest.getBookId())){
            throw new IllegalArgumentException("해당 id를 가지는 책이 없습니다. (" + borrowRequest.getBookId() + ")");
        }

        List<Borrow> borrows = borrowEntityRepository.findBorrowsByBookId(borrowRequest.getBookId());
        long borrow_count = borrows.stream().filter(b -> b.isExpired() || b.isBorrowing()).count();
        if(borrow_count > 0) {
            throw new IllegalStateException("해당 책은 이미 대여중인 책입니다.");
        }
    }
}
