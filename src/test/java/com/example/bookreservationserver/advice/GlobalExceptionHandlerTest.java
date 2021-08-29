package com.example.bookreservationserver.advice;

import com.example.bookreservationserver.skeleton.ControllerTest;
import com.example.bookreservationserver.user.controller.UserController;
import com.example.bookreservationserver.user.dto.AuthRequest;
import com.example.bookreservationserver.user.service.AuthService;
import com.example.bookreservationserver.user.service.JoinService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest extends ControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private JoinService joinService;
    @Mock
    private AuthService authService;

    @Test
    void handleMethodArgumentNotValidException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(AuthRequest.builder().email("1234").password("1234").build()))
        );

        //then
         String content = resultActions.andExpect(status().isBadRequest()).andDo(print()).andReturn().getResponse().getContentAsString();
         ErrorResponse errorResponse = gson.fromJson(content, ErrorResponse.class);
         assertEquals(errorResponse.getCode(), ErrorCode.INVALID_INPUT_VALUE.getCode());
    }


    @Test
    void handleHttpRequestMethodNotSupportedException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(AuthRequest.builder().email("1234").password("1234").build()))
        );

        //then
        String content = resultActions.andExpect(status().isMethodNotAllowed()).andDo(print()).andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = gson.fromJson(content, ErrorResponse.class);
        assertEquals(errorResponse.getCode(), ErrorCode.METHOD_NOT_ALLOWED.getCode());
    }

    @Test
    void handleIllegalStatementException() throws Exception {
        //given
        doThrow(new IllegalStateException("연산을 수행할 수 없는 상태입니다.")).when(authService).auth(any());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(AuthRequest.builder().email("1234@abcd").password("1234abcd").build()))
        );

        //then
        String content = resultActions.andExpect(status().isBadRequest()).andDo(print()).andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = gson.fromJson(content, ErrorResponse.class);
        assertEquals(errorResponse.getCode(), ErrorCode.ILLEGAL_STATE.getCode());
    }

    @Test
    void handleIllegalArgumentException() throws Exception {
        //given
        doThrow(new IllegalArgumentException("연산을 수행할 수 없는 인풋입니다.")).when(authService).auth(any());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(AuthRequest.builder().email("1234@abcd").password("1234abcd").build()))
        );

        //then
        String content = resultActions.andExpect(status().isBadRequest()).andDo(print()).andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = gson.fromJson(content, ErrorResponse.class);
        assertEquals(errorResponse.getCode(), ErrorCode.ILLEGAL_ARGUMENT.getCode());
    }

    @Test
    void handleException() throws Exception {
        //given
        doThrow(new RuntimeException("연산을 수행할 수 없습니다..")).when(authService).auth(any());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(AuthRequest.builder().email("1234@abcd").password("1234abcd").build()))
        );

        //then
        String content = resultActions.andExpect(status().isInternalServerError()).andDo(print()).andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = gson.fromJson(content, ErrorResponse.class);
        assertEquals(errorResponse.getCode(), ErrorCode.EXCEPTION.getCode());
    }

    @Override
    protected Object getController() {
        return controller;
    }
}