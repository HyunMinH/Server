package com.example.bookreservationserver.borrow.infra.repository;

import com.example.bookreservationserver.book.domain.aggregate.QBook;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepositoryCustom;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.bookreservationserver.borrow.domain.aggregate.QBorrow.borrow;

@RequiredArgsConstructor
public class BorrowRepositoryImpl implements BorrowRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<BorrowBookResponse> findBorrowBookAllByState(BorrowState state) {
        return findAll(borrow.state.eq(state));
    }

    @Override
    public List<BorrowBookResponse> findBorrowBookAllByUserId(Long userId) {
        return findAll(borrow.borrow_id.eq(userId));
    }

    @Override
    public List<BorrowBookResponse> findBorrowBookAllByUserIdAndState(Long userId, BorrowState state) {
        return findAll(borrow.borrow_id.eq(userId).and(borrow.state.eq(state)));
    }

    @Override
    public List<BorrowBookResponse> findBorrowBookByUserIdAndBookIdOrderByLatest(Long userId, Long bookId) {
        return null;
    }


    private List<BorrowBookResponse> findAll(Predicate predicate){
        return queryFactory
                .select(Projections.fields(BorrowBookResponse.class,
                        borrow.borrow_id.as("borrow_id"),
                        borrow.state.as("state"),
                        borrow.borrower.userId.as("user_id"),
                        borrow.borrower.userName.as("user_name"),
                        borrow.createdAt.as("created_at"),
                        borrow.expiredAt.as("expired_at"),
                        QBook.book.as("book")
                )).from(borrow)
                .join(QBook.book).on(borrow.bookId.eq(QBook.book.book_id))
                .where(predicate)
                .fetch();
    }
}
