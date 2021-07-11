package com.example.bookreservationserver.user.controller;

import com.example.bookreservationserver.user.dto.AuthRequest;
import com.example.bookreservationserver.user.dto.JoinRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import com.example.bookreservationserver.user.service.AuthService;
import com.example.bookreservationserver.user.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private JoinService joinService;
    @Autowired
    private AuthService authService;

    @PostMapping("/api/user/join")
    public UserResponse join(@RequestBody @Valid JoinRequest joinRequest){
        return joinService.join(joinRequest);
    }

    @PostMapping("/api/user/login")
    public UserResponse login(@RequestBody @Valid AuthRequest authRequest, HttpSession httpSession){
        httpSession.setAttribute("email", authRequest.getEmail());
        return authService.auth(authRequest);
    }
}
