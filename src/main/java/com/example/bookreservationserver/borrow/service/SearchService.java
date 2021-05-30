package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.repository.BorrowEntityRepository;
import com.example.bookreservationserver.borrow.dto.BorrowResponse;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final BorrowEntityRepository borrowEntityRepository;
    private final BookEntityRepository bookEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public List<BorrowResponse> getAllBorrowing() {
        List<Borrow> borrows = borrowEntityRepository.findAll();
        borrows.forEach(Borrow::setStateIfExpired);
        return joinWithBook(borrows.stream().filter(b -> b.isBorrowing() || b.isExpired()).collect(Collectors.toList()));
    }

    @Transactional
    public List<BorrowResponse> getAllExpired() {
        List<Borrow> borrows = borrowEntityRepository.findAll();
        borrows.forEach(Borrow::setStateIfExpired);
        return joinWithBook(borrows.stream().filter(Borrow::isExpired).collect(Collectors.toList()));
    }


    // below is for one user
    @Transactional
    public List<BorrowResponse> getMyBorrowingReservations(Long userId){
        checkUserExist(userId);

        List<Borrow> borrows = borrowEntityRepository.findBorrowsByBorrower_UserId(userId);
        borrows.forEach(Borrow::setStateIfExpired);
        return joinWithBook(borrows.stream().filter(b -> b.isBorrowing() || b.isExpired()).collect(Collectors.toList()));
    }

    @Transactional
    public List<BorrowResponse> getMyReservations(Long userId){
        checkUserExist(userId);

        List<Borrow> borrows = borrowEntityRepository.findBorrowsByBorrower_UserId(userId);
        return joinWithBook(borrows);
    }

    @Transactional
    public List<BorrowResponse> getMyExpired(Long userId) {
        checkUserExist(userId);

        List<Borrow> borrows = borrowEntityRepository.findBorrowsByBorrower_UserId(userId);
        borrows.forEach(Borrow::setStateIfExpired);
        return joinWithBook(borrows.stream().filter(Borrow::isExpired).collect(Collectors.toList()));
    }

    private void checkUserExist(Long userId){
        if(!userEntityRepository.existsById(userId))
            throw new IllegalArgumentException("해당 유저가 없습니다.");
    }

    private List<BorrowResponse> joinWithBook(List<Borrow> borrows){
        return borrows.stream().map(b ->{
            Book book = bookEntityRepository.findById(b.getBookId()).get();
            return new BorrowResponse(b, book);
        }).collect(Collectors.toList());
    }
}
