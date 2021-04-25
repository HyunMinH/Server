package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import com.example.bookreservationserver.user.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    public void auth(AuthRequest authRequest){
        checkExistUser(authRequest.getEmail());
        checkSamePassword(authRequest.getEmail(), authRequest.getPassword());
    }

    private void checkExistUser(String email){
        if(userEntityRepository.countByEmail(email) <= 0)
            throw new IllegalArgumentException("cannot find user.");
    }

    private void checkSamePassword(String email, String password){
        User user = userEntityRepository.findByEmail(email);
        if(!user.matchPassword(password))
            throw new IllegalArgumentException("password is not correct.");
    }
}
