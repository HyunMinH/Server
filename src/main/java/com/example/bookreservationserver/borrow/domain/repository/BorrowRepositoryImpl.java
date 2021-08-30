package com.example.bookreservationserver.borrow.domain.repository;

import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.bookreservationserver.book.domain.aggregate.QBook.book;
import static com.example.bookreservationserver.borrow.domain.aggregate.QBorrow.borrow;

@RequiredArgsConstructor
public class BorrowRepositoryImpl implements BorrowRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BorrowBookResponse> findBorrowbookAllByState(BorrowState state) {
        return find().where(borrow.state.eq(state)).fetch();
    }

    @Override
    public List<BorrowBookResponse> findBorrowbookAllByBorrower_UserId(Long userId) {
        return find().where(borrow.borrower.userId.eq(userId)).fetch();
    }

    @Override
    public List<BorrowBookResponse> findBorrowbookAllByBorrower_UserIdAndState(Long userId, BorrowState state) {
        return find().where(borrow.borrower.userId.eq(userId).and(borrow.state.eq(state))).fetch();
    }



    private JPAQuery<BorrowBookResponse> find(){
        return queryFactory
                .select(Projections.constructor(BorrowBookResponse.class,
                        borrow.id,
                        borrow.state,
                        borrow.createdAt,
                        borrow.expiredAt,
                        borrow.borrower.userId,
                        borrow.borrower.userName,
                        book
                )).from(borrow)
                .join(book).on(borrow.bookId.eq(book.id));
    }
}

