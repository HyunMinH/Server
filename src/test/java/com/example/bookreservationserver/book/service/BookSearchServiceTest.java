package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookDto;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {
    @InjectMocks
    BookSearchService bookSearchService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BorrowRepository borrowRepository;

    @Test
    @DisplayName("책 한권 정보 검색 성공")
    public void testOneBookSearchSuccess(){
        //given
        BookDto bookDto = BookDto.builder().id(1L).book_name("book1").build();
        doReturn(Optional.of(bookDto)).when(bookRepository).findBookDtoById(bookDto.getId());

        //when
        BookDto resultDto = bookSearchService.infoBook(bookDto.getId());

        //then
        assertEquals(resultDto.getId(), bookDto.getId());
    }

    @Test
    @DisplayName("책 한권 정보 검색 실패")
    public void testOneBookSearchFailed(){
        //given
        doReturn(Optional.empty()).when(bookRepository).findBookDtoById(any());

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bookSearchService.infoBook(1L));

        //then
        assertEquals(exception.getMessage(), "해당되는 책이 없습니다.");
    }

    @Test
    @DisplayName("책 여러 권 검색 성공")
    public void testAllBookSearchSuccess(){
        //given
        List<BookDto> bookDtoList = bookDtoList();
        doReturn(bookDtoList).when(bookRepository).findBookDtoAll();

        //when
        List<BookDto> resultDtoList = bookSearchService.infoBooks();

        //then
        assertEquals(resultDtoList.size(), bookDtoList.size());
        assertEquals(resultDtoList.get(1).getId(), bookDtoList.get(1).getId());
    }

    private List<BookDto> bookDtoList(){
        return List.of(
                BookDto.builder().id(1L).build(),
                BookDto.builder().id(2L).build(),
                BookDto.builder().id(3L).build(),
                BookDto.builder().id(4L).build(),
                BookDto.builder().id(5L).build(),
                BookDto.builder().id(6L).build()
        );
    }
}