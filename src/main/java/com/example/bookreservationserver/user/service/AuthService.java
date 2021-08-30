package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import com.example.bookreservationserver.user.dto.AuthRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse auth(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("해당하는 유저가 없습니다."));

        if (!user.matchPassword(authRequest.getPassword()))
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");

        return new UserResponse(user);
    }
}
