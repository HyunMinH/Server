package com.example.bookreservationserver.book.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookDeleteServiceTest {
    @InjectMocks
    private BookDeleteService service;

    @Mock
    private BookRepository repository;

    @Test
    @DisplayName("책 삭제 성공")
    public void testDeleteBookSuccess(){
        // given
        Book book = Book.builder().id(1L).book_name("book1").build();
        doReturn(Optional.of(book)).when(repository).findById(1L);

        // when
        service.deleteBook(1L);

        // then
        verify(repository, times(1)).delete(argThat(b->b.getId()==1L));
    }


    @Test
    @DisplayName("책 삭제 실패")
    public void testDeleteBookFailed(){
        // given
        doReturn(Optional.empty()).when(repository).findById(any());

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> service.deleteBook(1L));

        // then
        assertEquals(exception.getMessage(), "책을 찾을 수 없습니다.");
    }
}