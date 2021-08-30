package com.example.bookreservationserver.book.domain.repository;

import com.example.bookreservationserver.book.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryCustom {
    List<BookDto> findBookDtoAll();
    Optional<BookDto> findBookDtoById(Long bookId);
}
