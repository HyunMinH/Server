package com.example.bookreservationserver.user.controller;

import com.example.bookreservationserver.user.dto.JoinRequest;
import com.example.bookreservationserver.user.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class UserApi {
    @Autowired
    private JoinService joinService;

    @PostMapping("/api/user/join")
    public ResponseEntity join(@RequestBody @Valid JoinRequest joinRequest, Errors errors, HttpSession httpSession){
        if(errors.hasErrors()) {
            log.info(errors.getAllErrors().toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        joinService.join(joinRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}
