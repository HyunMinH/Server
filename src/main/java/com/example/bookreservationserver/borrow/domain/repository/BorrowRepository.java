package com.example.bookreservationserver.borrow.domain.repository;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long>, BorrowRepositoryCustom {
    List<Borrow> findBorrowsByBorrower_UserId(Long userId);
    List<Borrow> findBorrowsByBookId(Long bookId);
    List<Borrow> findBorrowsByBorrower_UserIdAndState(Long userId, BorrowState state);
    List<Borrow> findBorrowsByBookIdAndState(Long bookId, BorrowState state);
    List<Borrow> findBorrowsByState(BorrowState state);
}
