package com.example.bookreservationserver.book.domain.repository;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityRepository extends JpaRepository<Book, Long> {
}
