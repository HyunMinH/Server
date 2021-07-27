package com.example.bookreservationserver.borrow.domain.aggregate;

import com.example.bookreservationserver.borrow.domain.service.BorrowValidator;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Access(AccessType.FIELD)
@AllArgsConstructor
@Builder
public class Borrow {
    private static int defaultPeriodDay = 7;

    @Id
    @GeneratedValue
    Long borrow_id;

    @Embedded
    private Borrower borrower;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    private LocalDate createdAt;

    private LocalDate expiredAt;

    private Long bookId;

    protected Borrow(){ }

    public Borrow(BorrowRequest request){
        borrower = new Borrower(request.getBorrowerId(), request.getBorrowerName());
        state = BorrowState.BORROWING;
        setBorrowTime(defaultPeriodDay);
        bookId = request.getBookId();
    }

    public static Borrow createBorrow(BorrowRequest borrowRequest, BorrowValidator borrowValidator){
        borrowValidator.validate(borrowRequest);
        return new Borrow(borrowRequest);
    }

    private void setBorrowTime(int dayOfExpirationInterval){
        if(createdAt != null) throw new IllegalStateException("already set time");
        if(dayOfExpirationInterval <= 0) throw new IllegalArgumentException("interval must grater than 0");

        createdAt = LocalDate.now();
        expiredAt = createdAt.plusDays(dayOfExpirationInterval);
    }

    public void setStateIfExpired(){
        if(this.state != BorrowState.BORROWING) return;

        if(LocalDate.now().isAfter(expiredAt))
            this.state = BorrowState.EXPIRED;
    }

    public void returned(){
        if(state == BorrowState.RETURNED) throw new IllegalStateException("이미 반납이 완료되었습니다.");
        state = BorrowState.RETURNED;
    }

    public boolean isExpired(){
        setStateIfExpired();
        return this.state == BorrowState.EXPIRED;
    }

    public boolean isReturned(){
        setStateIfExpired();
        return this.state == BorrowState.RETURNED;
    }

    public boolean isBorrowing(){
        setStateIfExpired();
        return this.state == BorrowState.BORROWING;
    }


    // getter
    public Long getBorrow_id() { return borrow_id; }
    public Borrower getBorrower() { return borrower; }
    public BorrowState getState() { return state; }
    public LocalDate getCreatedAt() { return createdAt; }
    public LocalDate getExpiredAt() { return expiredAt; }
    public Long getBookId() { return bookId; }
}
