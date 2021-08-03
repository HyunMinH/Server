package com.example.bookreservationserver.borrow.domain.aggregate;

import com.example.bookreservationserver.borrow.domain.service.BorrowValidator;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Borrow {
    private static int defaultPeriodDay = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Embedded
    private Borrower borrower;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "expired_at")
    private LocalDate expiredAt;

    @Column(name = "book_id")
    private Long bookId;

    private Borrow(BorrowRequest request){
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
        createdAt = LocalDate.now();
        expiredAt = createdAt.plusDays(dayOfExpirationInterval);
    }

    public void expired(){
        if(state == BorrowState.RETURNED) throw new IllegalStateException("이미 반납이 완료되었습니다.");
        if(LocalDate.now().isBefore(expiredAt)) throw new IllegalStateException("아직 연체 기간이 남았습니다.");
        state = BorrowState.EXPIRED;
    }

    public void returned(){
        if(state == BorrowState.RETURNED) throw new IllegalStateException("이미 반납이 완료되었습니다.");
        state = BorrowState.RETURNED;
    }

    public boolean isExpired(){
        return this.state == BorrowState.EXPIRED;
    }

    public boolean isReturned(){
        return this.state == BorrowState.RETURNED;
    }

    public boolean isBorrowing(){
        return this.state == BorrowState.BORROWING;
    }
}
