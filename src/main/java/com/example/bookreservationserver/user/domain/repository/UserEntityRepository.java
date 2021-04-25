package com.example.bookreservationserver.user.domain.repository;

import com.example.bookreservationserver.user.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<User, Long> {
    int countByEmail(String email);
}
