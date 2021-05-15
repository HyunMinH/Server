package com.example.bookreservationserver.borrow.service;


import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.BorrowResponse;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {
    private BorrowEntityRepository borrowEntityRepository;
    private BookEntityRepository bookEntityRepository;
    private UserEntityRepository userEntityRepository;

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest borrowRequest){
        if(!userEntityRepository.existsById(borrowRequest.getBorrowerId()))
            throw new IllegalArgumentException("빌리려는 사용자의 id가 맞지 않습니다.");
        for(Long bookId : borrowRequest.getBookIds()){
            if(!bookEntityRepository.existsById(bookId))
                throw new IllegalArgumentException("다음의 id를 가지는 책이 없습니다." + bookId);
        }

        Borrow borrow = new Borrow(borrowRequest);
        borrowEntityRepository.save(borrow);
        return new BorrowResponse(borrow);
    }


    @Autowired
    public BorrowService(BorrowEntityRepository borrowEntityRepository, BookEntityRepository bookEntityRepository, UserEntityRepository userEntityRepository) {
        this.borrowEntityRepository = borrowEntityRepository;
        this.bookEntityRepository = bookEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }
}
