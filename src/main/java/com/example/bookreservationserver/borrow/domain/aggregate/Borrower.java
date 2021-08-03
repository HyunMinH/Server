package com.example.bookreservationserver.borrow.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Borrower {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;
}
