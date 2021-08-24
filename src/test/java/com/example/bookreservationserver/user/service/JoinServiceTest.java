package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.repository.UserRepository;
import com.example.bookreservationserver.user.dto.JoinRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class JoinServiceTest {
    @InjectMocks
    private JoinService service;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("회원가입 성공")
    public void testJoinSuccess(){
        //given
        JoinRequest joinRequest = joinRequest();
        doReturn(0).when(repository).countByEmail(any());

        //when
        UserResponse userResponse = service.join(joinRequest);

        //then
        assertEquals(userResponse.getEmail(), joinRequest.getEmail());
    }

    @Test
    @DisplayName("중복되는 이메일로 회원가입 실패")
    public void testJoinFailedWhenDuplicatedEamil(){
        //given
        JoinRequest joinRequest = joinRequest();
        doReturn(1).when(repository).countByEmail(any());

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->service.join(joinRequest));


        //then
        assertEquals(exception.getMessage(), "이메일이 중복됩니다.");
    }

    private JoinRequest joinRequest(){
        return JoinRequest.builder().email("good1234@naver.com").password("abcd1234").build();
    }
}