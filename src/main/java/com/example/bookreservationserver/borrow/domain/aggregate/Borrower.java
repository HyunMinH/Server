package com.example.bookreservationserver.borrow.domain.aggregate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Borrower {
    private Long userId;
    private String userName;

    protected Borrower(){

    }

    protected Borrower(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() { return userId; }

    public String getUserName() { return userName; }
}
