package com.example.bookreservationserver.borrow.domain.aggregate;

import com.example.bookreservationserver.borrow.dto.BorrowRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
public class Borrow {
    private static int defaultPeriodDay = 7;

    @Id
    @GeneratedValue
    Long borrow_id;

    @Embedded
    private Borrower borrower;

    @Enumerated(EnumType.STRING)
    private BorrowState state;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    @ElementCollection
    @CollectionTable(name="borrow_line", joinColumns = @JoinColumn(name="borrow_id"))
    private List<BorrowLine> borrowLines;

    protected Borrow(){ }

    public Borrow(BorrowRequest request){
        borrower = new Borrower(request.getBorrowerId(), request.getBorrowerName());
        state = BorrowState.BORROWING;
        setBorrowTime(defaultPeriodDay);
        setReservationLines(request.getBookIds());
    }

    private void setReservationLines(List<Long> bookIds){
        if(bookIds.size() <= 0) throw new IllegalArgumentException("You must have at least one book to borrow.");
        this.borrowLines = bookIds.stream().map(id -> new BorrowLine(id)).collect(Collectors.toList());
    }

    private void setBorrowTime(int dayOfExpirationInterval){
        if(createdAt != null) throw new IllegalStateException("already set time");
        if(dayOfExpirationInterval <= 0) throw new IllegalArgumentException("interval must grater than 0");

        createdAt = LocalDateTime.now();
        expiredAt = createdAt.plusDays(dayOfExpirationInterval);
    }

    public void setStateIfExpired(){
        if(LocalDateTime.now().isAfter(expiredAt))
            this.state = BorrowState.EXPIRED;
    }

    public void returned(){
        if(state == BorrowState.RETURNED) throw new IllegalStateException("already returned");
        state = BorrowState.RETURNED;
    }


    // getter
    public Long getBorrow_id() { return borrow_id; }
    public Borrower getBorrower() { return borrower; }
    public BorrowState getState() { return state; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiredAt() { return expiredAt; }
    public List<BorrowLine> getBorrowLines() { return borrowLines; }
}
