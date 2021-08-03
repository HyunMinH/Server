package com.example.bookreservationserver.borrow.domain.repository;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrower;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class BorrowRepositoryTest {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("생성 성공")
    public void testCreateSuccess(){
        //given

        //when
        List<Borrow> borrowList = borrowRepository.findAll();
        List<Book> bookList = bookRepository.findAll();

        //then
        assertTrue(borrowList.size() == 6);
        assertNotNull(borrowList.get(0).getId());


        assertTrue(bookList.size() == 6);
        assertNotNull(bookList.get(0).getBook_id());
        assertEquals(bookList.get(0).getBook_id(), borrowList.get(0).getBookId());
    }

    @Test
    @DisplayName("상태로 대여-책 검색 성공")
    public void testFindByStateSuccess(){
        //given

        //when
        List<BorrowBookResponse> borrowBookList1 = borrowRepository.findBorrowbookAllByState(BorrowState.BORROWING);
        List<BorrowBookResponse> borrowBookList2 = borrowRepository.findBorrowbookAllByState(BorrowState.EXPIRED);
        List<BorrowBookResponse> borrowBookList3 = borrowRepository.findBorrowbookAllByState(BorrowState.RETURNED);

        //then
        assertEquals(borrowBookList1.size(), 2);
        assertEquals(borrowBookList2.size(), 2);
        assertEquals(borrowBookList3.size(), 2);
    }

    @Test
    @DisplayName("유저 id로 대여-책 검색 성공")
    public void testFindByUserId(){
        //given

        //when
        List<BorrowBookResponse> borrowBookList1 = borrowRepository.findBorrowbookAllByBorrower_UserId(1L);
        List<BorrowBookResponse> borrowBookList2 = borrowRepository.findBorrowbookAllByBorrower_UserId(2L);

        //then
        assertEquals(borrowBookList1.size(), 3);
        assertEquals(borrowBookList2.size(), 3);
    }

    @Test
    @DisplayName("유저 id 및 책 대여 상태로 대여-책 검색 성공")
    public void testFindByUserIdAndState(){
        //given

        //when
        List<BorrowBookResponse> borrowBookList1 = borrowRepository
                .findBorrowbookAllByBorrower_UserIdAndState(1L, BorrowState.BORROWING);
        List<BorrowBookResponse> borrowBookList2 = borrowRepository
                .findBorrowbookAllByBorrower_UserIdAndState(1L, BorrowState.EXPIRED);
        List<BorrowBookResponse> borrowBookList3 = borrowRepository
                .findBorrowbookAllByBorrower_UserIdAndState(1L, BorrowState.RETURNED);

        List<BorrowBookResponse> borrowBookList4 = borrowRepository
                .findBorrowbookAllByBorrower_UserIdAndState(2L, BorrowState.BORROWING);
        List<BorrowBookResponse> borrowBookList5 = borrowRepository
                .findBorrowbookAllByBorrower_UserIdAndState(2L, BorrowState.EXPIRED);
        List<BorrowBookResponse> borrowBookList6 = borrowRepository
                .findBorrowbookAllByBorrower_UserIdAndState(2L, BorrowState.RETURNED);


        //then
        assertEquals(borrowBookList1.size(), 2);
        assertEquals(borrowBookList2.size(), 1);
        assertEquals(borrowBookList3.size(), 0);

        assertEquals(borrowBookList4.size(), 0);
        assertEquals(borrowBookList5.size(), 1);
        assertEquals(borrowBookList6.size(), 2);
    }

    @BeforeEach
    public void setUp(){
        List<Book> bookList = bookList();
        bookRepository.saveAll(bookList);
        borrowRepository.saveAll(borrowList(bookList));
    }

    @AfterEach
    public void tearDown(){
        borrowRepository.deleteAll();
        bookRepository.deleteAll();
    }

    public List<Book> bookList(){
        return List.of(
                Book.builder().book_name("name1").build(),
                Book.builder().book_name("name2").build(),
                Book.builder().book_name("name3").build(),
                Book.builder().book_name("name4").build(),
                Book.builder().book_name("name5").build(),
                Book.builder().book_name("name6").build()
        );
    }

    public List<Borrow> borrowList(List<Book> books){
        return List.of(
                Borrow.builder().bookId(books.get(0).getBook_id()).state(BorrowState.BORROWING).createdAt(LocalDate.now())
                .expiredAt(LocalDate.now().plusDays(7)).borrower(new Borrower(1L, "hong")).build(),
                Borrow.builder().bookId(books.get(1).getBook_id()).state(BorrowState.BORROWING).createdAt(LocalDate.now())
                        .expiredAt(LocalDate.now().plusDays(7)).borrower(new Borrower(1L, "hong")).build(),

                Borrow.builder().bookId(books.get(2).getBook_id()).state(BorrowState.EXPIRED).createdAt(LocalDate.now())
                        .expiredAt(LocalDate.now().plusDays(7)).borrower(new Borrower(1L, "hong")).build(),
                Borrow.builder().bookId(books.get(3).getBook_id()).state(BorrowState.EXPIRED).createdAt(LocalDate.now())
                        .expiredAt(LocalDate.now().plusDays(7)).borrower(new Borrower(2L, "kim")).build(),

                Borrow.builder().bookId(books.get(4).getBook_id()).state(BorrowState.RETURNED).createdAt(LocalDate.now())
                        .expiredAt(LocalDate.now().plusDays(7)).borrower(new Borrower(2L, "kim")).build(),
                Borrow.builder().bookId(books.get(5).getBook_id()).state(BorrowState.RETURNED).createdAt(LocalDate.now())
                        .expiredAt(LocalDate.now().plusDays(7)).borrower(new Borrower(2L, "kim")).build()
        );
    }
}