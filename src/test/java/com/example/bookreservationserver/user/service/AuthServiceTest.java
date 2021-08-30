package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import com.example.bookreservationserver.user.dto.AuthRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService service;

    @Mock
    private UserRepository repository;


    @Test
    @DisplayName("유저 인증 성공")
    public void testAuthSuccess(){
        //given
        AuthRequest authRequest = authRequest();
        doReturn(Optional.of(User.builder().email(authRequest.getEmail()).password(authRequest.getPassword()).build()))
                .when(repository).findByEmail(argThat(s -> s.equals(authRequest.getEmail())));

        //when
        UserResponse userResponse = service.auth(authRequest);

        //then
        assertEquals(userResponse.getEmail(), authRequest.getEmail());
    }


    @Test
    @DisplayName("해당하는 유저가 없어서 인증 실패")
    public void testAuthFailedWhenNotExistedUser(){
        //given
        AuthRequest authRequest = authRequest();
        doReturn(Optional.empty()).when(repository).findByEmail(any());

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->service.auth(authRequest));


        //then
        assertEquals(exception.getMessage(), "해당하는 유저가 없습니다.");
    }

    @Test
    @DisplayName("패스워드가 틀려서 인증 실패")
    public void testAuthFailedWhenPasswordDiff(){
        //given
        AuthRequest authRequest = authRequest();
        doReturn(Optional.of(User.builder().email(authRequest.getEmail()).password("zxcvzxcvzxcv").build()))
                .when(repository).findByEmail(argThat(s->s.equals(authRequest.getEmail())));

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->service.auth(authRequest));

        //then
        assertEquals(exception.getMessage(), "비밀번호가 맞지 않습니다.");
    }

    private AuthRequest authRequest(){
        return AuthRequest.builder().email("good1234@naver.com").password("abcd1234").build();
    }
}