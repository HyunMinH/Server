package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import com.example.bookreservationserver.user.dto.JoinRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse join(JoinRequest joinRequest){
        checkDuplicateId(joinRequest.getEmail());
        User user = saveAsNewUser(joinRequest);
        return new UserResponse(user);
    }

    private User saveAsNewUser(JoinRequest joinRequest) {
        User user = new User(joinRequest);
        userRepository.save(user);
        return user;
    }

    private void checkDuplicateId(String email){
        if(userRepository.countByEmail(email) > 0)
            throw new IllegalArgumentException("duplicated email.");
    }
}
