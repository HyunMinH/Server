package com.example.bookreservationserver.reservation.domain.repository;

import com.example.bookreservationserver.reservation.domain.aggregate.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationEntityRepository extends JpaRepository<Reservation, Long> {
}
