package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import com.example.bookreservationserver.user.dto.AuthRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse auth(AuthRequest authRequest){
        checkExistUser(authRequest.getEmail());
        User user = checkSamePassword(authRequest.getEmail(), authRequest.getPassword());
        return new UserResponse(user);
    }

    private void checkExistUser(String email){
        if(userRepository.countByEmail(email) <= 0)
            throw new IllegalArgumentException("cannot find user.");
    }

    private User checkSamePassword(String email, String password){
        User user = userRepository.findByEmail(email);
        if(!user.matchPassword(password))
            throw new IllegalArgumentException("password is not correct.");
        return user;
    }
}
