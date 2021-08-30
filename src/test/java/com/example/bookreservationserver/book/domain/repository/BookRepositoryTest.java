package com.example.bookreservationserver.book.domain.repository;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.dto.BookDto;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.aggregate.Borrower;
import com.example.bookreservationserver.skeleton.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class BookRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("BookDto 전부 가져오기 성공")
    public void testGetAllBookDtoSuccess(){
        //given

        //when
        List<BookDto> bookDtoList = bookRepository.findBookDtoAll();

        //then
        assertEquals(bookDtoList.size(), 6);
        assertTrue(bookDtoList.get(0).getBook_name().startsWith("book"));
    }

    @Test
    @DisplayName("BookDto 한 개 id로 가져오기 성공")
    public void testBookDtoByIdSuccess(){
        //given

        //when
        Book book = bookRepository.findAll().stream().findFirst().get();
        BookDto bookDto = bookRepository.findBookDtoById(book.getId()).get();

        //then
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getBook_name(), bookDto.getBook_name());
    }

    @Test
    @DisplayName("BookDto 한 개 id로 가져오기 실패!")
    public void testBookDtoByIdFailed(){
        //given

        //when
        Optional<BookDto> bookDtoOptional = bookRepository.findBookDtoById(Long.MAX_VALUE/2);

        //then
        assertTrue(bookDtoOptional.isEmpty());
    }

    @BeforeEach
    public void setUp() {
        List<Book> bookList = bookList();
        bookRepository.saveAll(bookList);
        borrowRepository.saveAll(borrowList(bookList));
    }

    @AfterEach
    public void tearDown(){
        bookRepository.deleteAll();;
        borrowRepository.deleteAll();
    }

    private List<Borrow> borrowList(List<Book> bookList){
        return bookList.stream().map(b -> Borrow.builder()
                .bookId(b.getId())
                .state(BorrowState.BORROWING)
                .borrower(new Borrower(b.getId(), "user1"))
                .createdAt(LocalDate.now())
                .expiredAt(LocalDate.now().plusDays(7))
                .build()).collect(Collectors.toList());
    }

    private List<Book>  bookList() {
        return Stream.iterate(0, i -> i + 1).limit(6).map(
                i -> Book.builder().book_name("book" + i).build()
        ).collect(Collectors.toList());
    }
}
