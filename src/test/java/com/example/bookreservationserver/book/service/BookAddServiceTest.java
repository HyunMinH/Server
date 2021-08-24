package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.book.dto.BookRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookAddServiceTest {

    @InjectMocks
    private BookAddService bookAddService;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 추가 성공")
    public void testAddBookSuccess(){
        // given
        BookRequestDto bookRequestDto = BookRequestDto.builder().book_name("book1").build();

        // when
        bookAddService.addBook(bookRequestDto);

        // then
        verify(bookRepository, times(1)).save(argThat(b -> b.getBook_name().equals("book1")));
    }
}