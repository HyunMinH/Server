package com.example.bookreservationserver.borrow.domain.repository;

import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;

import java.util.List;

public interface BorrowRepositoryCustom {
    List<BorrowBookResponse> findBorrowbookAllByState(BorrowState state);
    List<BorrowBookResponse> findBorrowbookAllByBorrower_UserId(Long userId);
    List<BorrowBookResponse> findBorrowbookAllByBorrower_UserIdAndState(Long userId, BorrowState state);
}
