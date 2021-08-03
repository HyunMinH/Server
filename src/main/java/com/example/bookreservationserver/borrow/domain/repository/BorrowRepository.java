package com.example.bookreservationserver.borrow.domain.repository;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long>, BorrowRepositoryCustom {
    List<Borrow> findBorrowsByBorrower_UserId(Long userId);
    List<Borrow> findBorrowsByBookId(Long bookId);
}