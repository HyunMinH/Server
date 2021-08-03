package com.example.bookreservationserver.user.controller;

import com.example.bookreservationserver.advice.ErrorCode;
import com.example.bookreservationserver.user.dto.JoinRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    public static JoinRequest correctRequest = new JoinRequest("gildong", "01012341234", "gildong@naver.com", "abcd1234");

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    public void validateJoinRequestName() throws Exception {
        JoinRequest joinRequest = new JoinRequest("honggildong2", correctRequest.getPhoneNum()
                , correctRequest.getEmail(), correctRequest.getPassword());

        String expectedFailRequest = objectMapper.writeValueAsString(joinRequest);
        String expectedSuccessRequest = objectMapper.writeValueAsString(correctRequest);

        // 유저의 이름이 10자보다 길어서 실패하는 경우
        mockMvc.perform(post("/api/user/join")
                .contentType("application/json") // json 타입으로 request body를 보낸다.
                .content(expectedFailRequest))
                .andExpect(status().isBadRequest()) // 400 status code 응답을 예상한다.
                .andDo(print()) // 결과를 출력한다.
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_INPUT_VALUE.getCode()))); // code 파라미터의 내용이 COMMON_001 인지 확인한다.

    }

    public void validateJoinRequestPhoneNum(){

    }

    public void validateJoinRequestEmail(){

    }

    public void validateJoinRequestPassword(){

    }
}