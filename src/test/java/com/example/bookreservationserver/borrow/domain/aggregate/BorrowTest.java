package com.example.bookreservationserver.borrow.domain.aggregate;

import com.example.bookreservationserver.borrow.domain.service.BorrowValidator;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class BorrowTest {
    @Mock
    private BorrowValidator borrowValidator;

    @Test
    @DisplayName("생성 완료")
    public void testCreateBorrowSuccess(){
        //given
        final BorrowRequest borrowRequest = borrowRequest();
        doNothing().when(borrowValidator).validate(any());

        //when
        Borrow borrow = Borrow.createBorrow(borrowRequest, borrowValidator);

        //then
        assertEquals(borrow.getBorrower().getUserId(), borrowRequest.getBorrowerId());
        assertEquals(borrow.getBorrower().getUserName(), borrowRequest.getBorrowerName());
        assertEquals(borrow.getBookId() ,borrowRequest.getBookId());
        assertEquals(borrow.getState(), BorrowState.BORROWING);
        assertNotNull(borrow.getCreatedAt());
        assertNotNull(borrow.getExpiredAt());
    }


    @Test
    @DisplayName("연체로 변경 성공")
    public void testSetExpiredSuccess(){
        //given
        Borrow borrow = Borrow.builder().state(BorrowState.BORROWING)
                .expiredAt(LocalDate.now().minusDays(1)).build();

        //when
        borrow.expired();

        //then
        assertEquals(borrow.getState(), BorrowState.EXPIRED);
    }

    @Test
    @DisplayName("연체로 변경 실패")
    public void testSetExpiredFailed(){
        //given
        Borrow borrow1 = Borrow.builder().state(BorrowState.RETURNED).build();
        Borrow borrow2 = Borrow.builder().state(BorrowState.BORROWING).expiredAt(LocalDate.now().plusDays(1)).build();

        //when
        IllegalStateException exception1 = assertThrows(IllegalStateException.class, ()->borrow1.expired());
        IllegalStateException exception2 = assertThrows(IllegalStateException.class, ()->borrow2.expired());

        //then
        assertEquals(exception1.getMessage(), "이미 반납이 완료되었습니다.");
        assertEquals(exception2.getMessage(), "아직 연체 기간이 남았습니다.");
    }

    @Test
    @DisplayName("반납 성공")
    public void testReturnSuccess(){
        //given
        Borrow borrow1 = Borrow.builder().state(BorrowState.BORROWING).build();
        Borrow borrow2 = Borrow.builder().state(BorrowState.EXPIRED).build();

        //when
        borrow1.returned();
        borrow2.returned();

        assertEquals(borrow1.getState(), BorrowState.RETURNED);
        assertEquals(borrow2.getState(), BorrowState.RETURNED);
    }

    @Test
    @DisplayName("반납 실패")
    public void testReturnFailed(){
        //given
        Borrow borrow1 = Borrow.builder().state(BorrowState.RETURNED).build();

        //when
        IllegalStateException exception1 = assertThrows(IllegalStateException.class,()-> borrow1.returned());

        //then
        assertEquals(exception1.getMessage(), "이미 반납이 완료되었습니다.");
    }

    @Test
    @DisplayName("상태 정상 반환 성공")
    public void testStateSuccess(){
        //given
        Borrow borrow1 = Borrow.builder().state(BorrowState.BORROWING).build();
        Borrow borrow2 = Borrow.builder().state(BorrowState.EXPIRED).build();
        Borrow borrow3 = Borrow.builder().state(BorrowState.RETURNED).build();

        //when
        boolean result1 = borrow1.isBorrowing();
        boolean result2 = borrow2.isExpired();
        boolean result3 = borrow3.isReturned();

        //then
        assertEquals(result1, true);
        assertEquals(result2, true);
        assertEquals(result3, true);
    }

    @Test
    @DisplayName("상태 정상 반환 실패")
    public void testStateFailed(){
        //given
        Borrow borrow1 = Borrow.builder().state(BorrowState.BORROWING).build();
        Borrow borrow2 = Borrow.builder().state(BorrowState.EXPIRED).build();
        Borrow borrow3 = Borrow.builder().state(BorrowState.RETURNED).build();

        //when
        boolean result1 = borrow1.isExpired();
        boolean result2 = borrow2.isReturned();
        boolean result3 = borrow3.isBorrowing();

        //then
        assertEquals(result1, false);
        assertEquals(result2, false);
        assertEquals(result3, false);
    }

    private BorrowRequest borrowRequest(){
        return BorrowRequest.builder()
                .borrowerId(1L)
                .bookId(2L)
                .borrowerName("honggildong")
                .build();
    }
}