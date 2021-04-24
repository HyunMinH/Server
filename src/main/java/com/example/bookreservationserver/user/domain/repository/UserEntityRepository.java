package com.example.bookreservationserver.user.domain.repository;

import com.example.bookreservationserver.user.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<User, Long> {
}
