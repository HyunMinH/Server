package com.example.bookreservationserver.borrow.domain.service;

import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowValidatorTest {
    @InjectMocks
    private BorrowValidator borrowValidator;

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 없은 경우 체크 성공")
    public void testCheckNoUserExceptionSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().build();
        doReturn(false).when(userRepository).existsById(any());

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class
                , ()-> borrowValidator.validate(borrowRequest));

        //then
        assertEquals(exception.getMessage(), "빌리려는 사용자의 id가 맞지 않습니다.");
    }

    @Test
    @DisplayName("연체된 책이 있는 경우 체크 성공")
    public void testCheckExistExpiredBookExceptionSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().build();
        doReturn(true).when(userRepository).existsById(any());
        doReturn(expiredList()).when(borrowRepository).findBorrowsByBorrower_UserId(any());


        //when
        IllegalStateException exception = assertThrows(IllegalStateException.class
                , ()-> borrowValidator.validate(borrowRequest));

        //then
        assertEquals(exception.getMessage(), "연체된 책이 있습니다.");
    }

    @Test
    @DisplayName("이미 3권 이상 빌린 경우 체크 성공")
    public void testCheckAlreadyOverBorrowExceptionSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().build();
        doReturn(true).when(userRepository).existsById(any());
        doReturn(alreadyOverBorrowList()).when(borrowRepository).findBorrowsByBorrower_UserId(any());

        //when
        IllegalStateException exception = assertThrows(IllegalStateException.class
                , ()-> borrowValidator.validate(borrowRequest));

        //then
        assertEquals(exception.getMessage(), "3권 이상 빌릴 수 없습니다.");
    }

    @Test
    @DisplayName("해당 책이 없는 경우 체크 성공")
    public void testCheckNonBookExceptionSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().bookId(1L).build();
        doReturn(true).when(userRepository).existsById(any());
        doReturn(borrowingNotOverList()).when(borrowRepository).findBorrowsByBorrower_UserId(any());
        doReturn(false).when(bookRepository).existsById(any());

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class
                , ()-> borrowValidator.validate(borrowRequest));

        //then
        assertEquals(exception.getMessage(), "해당 id를 가지는 책이 없습니다. (1)");
    }

    @Test
    @DisplayName("해당 책이 이미 대여중인 경우 체크 성공")
    public void testCheckAlreadyBorrowExceptionSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().bookId(1L).build();
        doReturn(true).when(userRepository).existsById(any());
        doReturn(borrowingNotOverList()).when(borrowRepository).findBorrowsByBorrower_UserId(any());
        doReturn(true).when(bookRepository).existsById(any());
        doReturn(borrowingNotOverList()).when(borrowRepository).findBorrowsByBookId(any());


        //when
        IllegalStateException exception = assertThrows(IllegalStateException.class
                , ()-> borrowValidator.validate(borrowRequest));

        //then
        assertEquals(exception.getMessage(), "해당 책은 이미 대여중인 책입니다.");
    }

    @Test
    @DisplayName("해당 책이 이미 연체중인 경우 체크 성공")
    public void testCheckAlreadyExpiredExceptionSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().bookId(1L).build();
        doReturn(true).when(userRepository).existsById(any());
        doReturn(borrowingNotOverList()).when(borrowRepository).findBorrowsByBorrower_UserId(any());
        doReturn(true).when(bookRepository).existsById(any());
        doReturn(expiredList()).when(borrowRepository).findBorrowsByBookId(any());


        //when
        IllegalStateException exception = assertThrows(IllegalStateException.class
                , ()-> borrowValidator.validate(borrowRequest));

        //then
        assertEquals(exception.getMessage(), "해당 책은 이미 대여중인 책입니다.");
    }

    @Test
    @DisplayName("검증 성공")
    public void testValidateSuccess(){
        //given
        final BorrowRequest borrowRequest = BorrowRequest.builder().bookId(1L).build();
        doReturn(true).when(userRepository).existsById(any());
        doReturn(borrowingNotOverList()).when(borrowRepository).findBorrowsByBorrower_UserId(any());
        doReturn(true).when(bookRepository).existsById(any());
        doReturn(retunedList()).when(borrowRepository).findBorrowsByBookId(any());


        //when
        borrowValidator.validate(borrowRequest);

        //then
        verify(borrowRepository, times(1)).findBorrowsByBookId(any());
    }

    public List<Borrow> expiredList(){
        return List.of(
                Borrow.builder().state(BorrowState.EXPIRED).build()
        );
    }

    public List<Borrow> alreadyOverBorrowList(){
        return List.of(
                Borrow.builder().state(BorrowState.BORROWING).build(),
                Borrow.builder().state(BorrowState.BORROWING).build(),
                Borrow.builder().state(BorrowState.BORROWING).build()
        );
    }

    public List<Borrow> borrowingNotOverList(){
        return List.of(
                Borrow.builder().state(BorrowState.BORROWING).bookId(1L).build(),
                Borrow.builder().state(BorrowState.BORROWING).bookId(2L).build()
        );
    }


    public List<Borrow> retunedList(){
        return List.of(
                Borrow.builder().state(BorrowState.RETURNED).bookId(1L).build(),
                Borrow.builder().state(BorrowState.RETURNED).bookId(2L).build()
        );
    }
}