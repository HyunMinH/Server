package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ReturnServiceTest {

    @InjectMocks
    private ReturnService returnService;

    @Mock
    private BorrowRepository borrowRepository;

    @Test
    @DisplayName("반납 성공")
    public void testReturnSuccess(){
        //given
        List<Borrow> borrowList = borrowingList();
        doReturn(borrowList).when(borrowRepository).findBorrowsByBookId(isA(Long.class));

        //when
        returnService.returnBook(1L);
    }

    @Test
    @DisplayName("책 존재하지 않을 시 반납 실패")
    public void testReturnFailedWhenNotExisted(){
        //given
        doReturn(Collections.emptyList()).when(borrowRepository).findBorrowsByBookId(isA(Long.class));

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->returnService.returnBook(1L));

        //then
        assertEquals(exception.getMessage(), "해당 책이 존재하지 않습니다");
    }

    @Test
    @DisplayName("이미 반납되었을 때 반납 실패")
    public void testReturnFailedWhenAlreadyReturned(){
        //given
        final List<Borrow> returnedList = returnedList();
        doReturn(returnedList).when(borrowRepository).findBorrowsByBookId(isA(Long.class));

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->returnService.returnBook(1L));

        //then
        assertEquals(exception.getMessage(), "이미 반납되었습니다.");
    }

    private List<Borrow> returnedList(){
        return List.of(
          Borrow.builder().state(BorrowState.RETURNED).build()
        );
    }
    private List<Borrow> borrowingList(){
        return List.of(
                Borrow.builder().expiredAt(now().plusDays(1)).state(BorrowState.BORROWING).build()
        );
    }
    private List<Borrow> expiredList(){
        return List.of(
                Borrow.builder().state(BorrowState.EXPIRED).build()
        );
    }
}