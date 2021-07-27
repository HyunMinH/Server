package com.example.bookreservationserver.borrow.domain.repository;

import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;

import java.util.List;

public interface BorrowRepositoryCustom {
    List<BorrowBookResponse> findBorrowBookAllByState(BorrowState state);
    List<BorrowBookResponse> findBorrowBookAllByUserId(Long userId);
    List<BorrowBookResponse> findBorrowBookAllByUserIdAndState(Long userId, BorrowState state);

    List<BorrowBookResponse> findBorrowBookByUserIdAndBookIdOrderByLatest(Long userId, Long bookId);
}
