package com.example.bookreservationserver.user.controller;

import com.example.bookreservationserver.skeleton.ControllerTest;
import com.example.bookreservationserver.user.dto.AuthRequest;
import com.example.bookreservationserver.user.dto.JoinRequest;
import com.example.bookreservationserver.user.dto.UserResponse;
import com.example.bookreservationserver.user.service.AuthService;
import com.example.bookreservationserver.user.service.JoinService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest extends ControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private JoinService joinService;

    @Mock
    private AuthService authService;

    @Test
    @DisplayName("회원가입 성공")
    public void testJoinSuccess() throws Exception {
        //given
        JoinRequest joinRequest = JoinRequest.builder()
                .email("good1234@naver.com").name("name1").password("abcd1234").phoneNum("010-1234-1234").build();

        doReturn(UserResponse.builder().email(joinRequest.getEmail()).name(joinRequest.getName()).build())
                .when(joinService).join(argThat(jr -> jr.getEmail().equals(joinRequest.getEmail())));

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(joinRequest))
        );

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UserResponse userResponse = gson.fromJson(result.getResponse().getContentAsString(), UserResponse.class);
        assertEquals(userResponse.getName(), joinRequest.getName());
        assertEquals(userResponse.getEmail(), joinRequest.getEmail());
    }

    @Test
    @DisplayName("형식이 맞지 않아 회원가입 실패")
    public void testJoinFailed() throws Exception{
        //given
        JoinRequest joinRequest1 = JoinRequest.builder().email("good1234@naver.com").build();
        JoinRequest joinRequest2 = JoinRequest.builder().email("good1234naver.com") // 이메일 형식
                .name("name1").password("abcd1234").phoneNum("010-1234-1234").build();
        JoinRequest joinRequest3 = JoinRequest.builder().email("good1234@naver.com")
                .name("name1").password("abcd").phoneNum("010-1234-1234").build(); // 비번 형식
        JoinRequest joinRequest4 = JoinRequest.builder().email("good1234@naver.com")
                .name("name1").password("abcd1234").phoneNum("010-1234-12345").build(); // 전화번호 형식

        //when
        final ResultActions resultActions1 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(joinRequest1))
        );

        final ResultActions resultActions2 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(joinRequest2))
        );

        final ResultActions resultActions3 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(joinRequest3))
        );

        final ResultActions resultActions4 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(joinRequest4))
        );

        //then
        resultActions1.andExpect(status().isBadRequest()).andDo(print());
        resultActions2.andExpect(status().isBadRequest()).andDo(print());
        resultActions3.andExpect(status().isBadRequest()).andDo(print());
        resultActions4.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("로그인 성공")
    public void testAuthSuccess() throws Exception {
        //given
        AuthRequest authRequest = AuthRequest.builder().email("um1234@naver.com").password("1234abcd").build();
        doReturn(UserResponse.builder().email(authRequest.getEmail()).build())
                .when(authService).auth(argThat(ar -> ar.getEmail().equals(authRequest.getEmail())));


        //then
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(authRequest))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        UserResponse userResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(), UserResponse.class);
        assertEquals(userResponse.getEmail(), authRequest.getEmail());
    }

    @Test
    @DisplayName("형식 때문에 로그인 실패")
    public void testAuthFailed() throws Exception{
        //given
        AuthRequest authRequest1 = AuthRequest.builder().email("um1234@naver.com").build();
        AuthRequest authRequest2 = AuthRequest.builder().email("um1234naver.com").password("abcd1234").build();

        //then
        final ResultActions resultActions1 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(authRequest1))
        );
        final ResultActions resultActions2 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(authRequest2))
        );

        //then
        resultActions1.andExpect(status().isBadRequest()).andDo(print());
        resultActions2.andExpect(status().isBadRequest()).andDo(print());
    }


    @Override
    protected Object getController() {
        return userController;
    }
}