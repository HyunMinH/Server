package com.example.bookreservationserver.book.domain.repository;

import com.example.bookreservationserver.book.dto.BookDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.example.bookreservationserver.book.domain.aggregate.QBook.book;
import static com.example.bookreservationserver.borrow.domain.aggregate.QBorrow.borrow;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookDto> findBookDtoAll() {
        return find().fetch();
    }

    @Override
    public Optional<BookDto> findBookDtoById(Long bookId){
        return Optional.ofNullable(find().where(book.id.eq(bookId)).fetchFirst());
    }

    private JPAQuery<BookDto> find(){
        return queryFactory
                .select(Projections.constructor(BookDto.class,
                        book.id,
                        book.book_name,
                        book.author,
                        book.library,
                        book.publisher,
                        book.publication_date,
                        book.image_url,
                        borrow.state
                )).from(book)
                .join(borrow).on(book.id.eq(borrow.bookId));
    }
}
