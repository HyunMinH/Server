package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BorrowSearchServiceTest {

    @InjectMocks
    private BorrowSearchService borrowSearchService;

    @Mock
    private BorrowRepository borrowRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("모든 대여중인 기록 가져오기 성공")
    public void testGetAllBorrowingSuccess(){
        //given
        final List<BorrowBookResponse> borrowBookList = borrowBookList().stream().filter(b -> b.getState()==BorrowState.BORROWING).collect(Collectors.toList());
        doReturn(borrowBookList).when(borrowRepository).findBorrowbookAllByState(BorrowState.BORROWING);

        //when
        final List<BorrowBookResponse> allBorrowings = borrowSearchService.getAllBorrowing();

        //then
        assertEquals(allBorrowings.size(), 2);
    }

    @Test
    @DisplayName("모든 연체중인 기록 가져오기 성공")
    public void testGetAllExpiredSuccess() {
        //given
        final List<BorrowBookResponse> borrowBookList = borrowBookList().stream().filter(b -> b.getState() == BorrowState.EXPIRED).collect(Collectors.toList());
        doReturn(borrowBookList).when(borrowRepository).findBorrowbookAllByState(BorrowState.EXPIRED);

        //when
        final List<BorrowBookResponse> allBorrowings = borrowSearchService.getAllExpired();

        //then
        assertEquals(allBorrowings.size(), 2);
    }

    @Test
    @DisplayName("유저의 현재 대여중인 목록 가져오기 성공")
    public void testGetOneUserBorrowingsSuccess(){
        //given
        doReturn(true).when(userRepository).existsById(1L);

        final List<BorrowBookResponse> borrowBookResponseList = borrowBookList().stream()
                .filter(b->(b.getState()==BorrowState.BORROWING || b.getState()==BorrowState.EXPIRED)).collect(Collectors.toList());
        doReturn(borrowBookResponseList).when(borrowRepository).findBorrowbookAllByBorrower_UserIdAndState(1L, BorrowState.BORROWING);

        //when
        final List<BorrowBookResponse> myAllBorrowings = borrowSearchService.getMyBorrowings(1L);

        //then
        assertEquals(myAllBorrowings.size(), 4);
    }

    @Test
    @DisplayName("유저의 현재 대여중인 목록 가져오기 실패")
    public void testGetOneUserBorrowingsFailed(){
        // given
        doReturn(false).when(userRepository).existsById(-1L);

        // when
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class
                , ()->borrowSearchService.getMyBorrowings(-1L));

        // then
        assertEquals(exception.getMessage(), "해당 유저가 없습니다.");
    }


    @Test
    @DisplayName("유저의 모든 대여 이력 가져오기 성공")
    public void testGetOneUserAllSuccess(){
        //given
        doReturn(true).when(userRepository).existsById(isA(Long.class));

        final List<BorrowBookResponse> borrowBookResponseList = borrowBookList();
        doReturn(borrowBookResponseList).when(borrowRepository).findBorrowbookAllByBorrower_UserId(1L);

        //when
        final List<BorrowBookResponse> myAllReservations = borrowSearchService.getMyReservations(1L);

        //then
        assertEquals(myAllReservations.size(), 6);
    }

    @Test
    @DisplayName("유저의 모든 연체 이력 가져오기 성공")
    public void testGetOneUserAllExpiredSuccess(){
        //given
        doReturn(true).when(userRepository).existsById(isA(Long.class));

        final List<BorrowBookResponse> borrowBookResponseList = borrowBookList().stream().filter(b->b.getState()==BorrowState.EXPIRED).collect(Collectors.toList());
        doReturn(borrowBookResponseList).when(borrowRepository).findBorrowbookAllByBorrower_UserIdAndState(1L, BorrowState.EXPIRED);

        //when
        final List<BorrowBookResponse> myAllReservations = borrowSearchService.getMyExpired(1L);

        //then
        assertEquals(myAllReservations.size(), 2);
    }

    private List<BorrowBookResponse> borrowBookList(){
        return List.of(
                BorrowBookResponse.builder().state(BorrowState.BORROWING).build(),
                BorrowBookResponse.builder().state(BorrowState.BORROWING).build(),
                BorrowBookResponse.builder().state(BorrowState.EXPIRED).build(),
                BorrowBookResponse.builder().state(BorrowState.EXPIRED).build(),
                BorrowBookResponse.builder().state(BorrowState.RETURNED).build(),
                BorrowBookResponse.builder().state(BorrowState.RETURNED).build()
        );
    }



    private List<Borrow> borrowList(){
        return List.of(
                Borrow.builder().expiredAt(LocalDate.now().plusDays(1)).state(BorrowState.BORROWING).build(),
                Borrow.builder().expiredAt(LocalDate.now().minusDays(1)).state(BorrowState.BORROWING).build(),
                Borrow.builder().expiredAt(LocalDate.now().minusDays(1)).state(BorrowState.EXPIRED).build(),
                Borrow.builder().expiredAt(LocalDate.now().minusDays(1)).state(BorrowState.EXPIRED).build(),
                Borrow.builder().expiredAt(LocalDate.now().minusDays(1)).state(BorrowState.RETURNED).build(),
                Borrow.builder().expiredAt(LocalDate.now().minusDays(1)).state(BorrowState.RETURNED).build()
        );
    }
}