package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnService {
    private final BorrowRepository borrowRepository;

    @Transactional
    public void returnBook(Long bookId){
        List<Borrow> borrows = borrowRepository.findBorrowsByBookId(bookId);
        if(borrows.isEmpty()) throw new IllegalArgumentException("해당 책이 존재하지 않습니다");

        Borrow borrow = borrows.get(borrows.size()-1);
        if(borrow.isReturned()) throw new IllegalArgumentException("이미 반납되었습니다.");
        borrow.returned();
    }
}
