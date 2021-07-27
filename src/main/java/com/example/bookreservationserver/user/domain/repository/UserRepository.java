package com.example.bookreservationserver.user.domain.repository;

import com.example.bookreservationserver.user.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    int countByEmail(String email);
}
