package com.example.bookreservationserver.borrow.domain.service;

import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowValidator {
    private final BorrowEntityRepository borrowEntityRepository;
    private final BookEntityRepository bookEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public void validate(BorrowRequest borrowRequest){
        if(!userEntityRepository.existsById(borrowRequest.getBorrowerId()))
            throw new IllegalArgumentException("빌리려는 사용자의 id가 맞지 않습니다.");

        List<Borrow> usersBorrows = borrowEntityRepository.findBorrowsByBorrower_UserId(borrowRequest.getBorrowerId());

        if(usersBorrows.stream().anyMatch(Borrow::isExpired)){
            throw new IllegalStateException("연체된 책이 있습니다.");
        }

        if(usersBorrows.stream().filter(Borrow::isBorrowing).count() >= 3){
            throw new IllegalStateException("3권 이상 빌릴 수 없습니다");
        }

        if(!bookEntityRepository.existsById(borrowRequest.getBookId())){
            throw new IllegalArgumentException("해당 id를 가지는 책이 없습니다. (" + borrowRequest.getBookId() + ")");
        }

        List<Borrow> borrows = borrowEntityRepository.findBorrowsByBookId(borrowRequest.getBookId());
        if(borrows.stream().anyMatch(Borrow::isBorrowing) || borrows.stream().anyMatch(Borrow::isExpired)){
            throw new IllegalStateException("해당 책은 이미 대여중인 책입니다.");
        }
    }
}
