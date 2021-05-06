package com.example.bookreservationserver.borrow.service;


import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    private BorrowEntityRepository borrowEntityRepository;

    public void borrowBook(BorrowRequest borrowRequest){
        Borrow borrow = new Borrow(borrowRequest);
        borrowEntityRepository.save(borrow);
    }

    @Autowired
    public BorrowService(BorrowEntityRepository borrowEntityRepository) {
        this.borrowEntityRepository = borrowEntityRepository;
    }
}
