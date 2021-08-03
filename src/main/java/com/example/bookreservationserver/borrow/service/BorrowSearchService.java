package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BorrowSearchService {
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<BorrowBookResponse> getAllBorrowing() {
        return borrowRepository.findBorrowbookAllByState(BorrowState.BORROWING);
    }

    @Transactional
    public List<BorrowBookResponse> getAllExpired() {
        return borrowRepository.findBorrowbookAllByState(BorrowState.EXPIRED);
    }


    // below is for one user
    @Transactional
    public List<BorrowBookResponse> getMyBorrowings(Long userId){
        checkUserExist(userId);
        return borrowRepository.findBorrowbookAllByBorrower_UserIdAndState(userId, BorrowState.BORROWING);
    }

    @Transactional
    public List<BorrowBookResponse> getMyReservations(Long userId){
        checkUserExist(userId);
        return borrowRepository.findBorrowbookAllByBorrower_UserId(userId);
    }

    @Transactional
    public List<BorrowBookResponse> getMyExpired(Long userId) {
        checkUserExist(userId);
        return borrowRepository.findBorrowbookAllByBorrower_UserIdAndState(userId, BorrowState.EXPIRED);
    }

    private void checkUserExist(Long userId){
        if(!userRepository.existsById(userId))
            throw new IllegalArgumentException("해당 유저가 없습니다.");
    }
}
