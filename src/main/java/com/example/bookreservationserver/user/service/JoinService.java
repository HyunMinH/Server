package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import com.example.bookreservationserver.user.dto.JoinRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse join(JoinRequest joinRequest) {
        if (userRepository.countByEmail(joinRequest.getEmail()) > 0)
            throw new IllegalArgumentException("이메일이 중복됩니다.");

        User user = new User(joinRequest);
        userRepository.save(user);
        return new UserResponse(user);
    }
}
