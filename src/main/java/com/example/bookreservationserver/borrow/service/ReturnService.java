package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReturnService {
    private BorrowEntityRepository borrowEntityRepository;

    @Transactional
    public void returnBook(Long bookId){
        Optional<Borrow> bookOptional = borrowEntityRepository.findById(bookId);
        if(bookOptional.isEmpty()) throw new IllegalArgumentException("cannot found book by bookId[" +bookId + "]");
        bookOptional.get().returned();
    }

    @Autowired
    public ReturnService(BorrowEntityRepository borrowEntityRepository) {
        this.borrowEntityRepository = borrowEntityRepository;
    }
}
