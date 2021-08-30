package com.example.bookreservationserver.user.domain.repository;

import com.example.bookreservationserver.user.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    int countByEmail(String email);
}
