package com.example.bookreservationserver.borrow.controller;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.domain.aggregate.BorrowState;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.ReturnResponse;
import com.example.bookreservationserver.borrow.service.BorrowSearchService;
import com.example.bookreservationserver.borrow.service.BorrowService;
import com.example.bookreservationserver.borrow.service.ReturnService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BorrowControllerTest {

    @InjectMocks
    private BorrowController borrowController;

    @Mock
    private BorrowService borrowService;
    @Mock
    private ReturnService returnService;
    @Mock
    private BorrowSearchService searchService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(borrowController).build();
        gson = new Gson();
    }

    @Test
    @DisplayName("대여하기 성공")
    void testBorrowSuccess() throws Exception {
        //given
        final BorrowRequest borrowRequestDto = BorrowRequest.builder()
                .borrowerId(1L).borrowerName("hongildong").bookId(2L).build();
        final Borrow borrow = Borrow.builder().state(BorrowState.BORROWING).build();

        doReturn(borrow).when(borrowService).borrowBook(argThat(b -> b.getBorrowerId() == 1));

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(borrowRequestDto))
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
        final Borrow response = gson.fromJson(mvcResult.getResponse().getContentAsString(), Borrow.class);
        assertEquals(response.getState(), BorrowState.BORROWING);
    }

    @Test
    @DisplayName("반납 완료 성공")
    void testReturnBook() throws Exception{
        //given
        doNothing().when(returnService).returnBook(any());

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/borrow/1/return")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final ReturnResponse returnResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(), ReturnResponse.class);
        assertEquals(returnResponse.getResult(), "반납이 완료되었습니다.");
    }

    @Test
    @DisplayName("모든 유저의 대여 중인 기록 가져오기 성공")
    void testAllBorrowing() throws Exception{
        //given
        doReturn(Collections.emptyList()).when(searchService).getAllBorrowing();

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/borrow/borrowing")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final List<BorrowBookResponse> borrowBookRespons = gson.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<BorrowBookResponse>>(){}.getType());
        assertEquals(borrowBookRespons.size(), 0);
    }

    @Test
    @DisplayName("유저의 모든 연체 기록 가져오기 성공")
    void testAllExpired() throws Exception{
        //given
        doReturn(Collections.emptyList()).when(searchService).getAllExpired();

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/borrow/expired")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final List<BorrowBookResponse> borrowBookRespons = gson.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<BorrowBookResponse>>(){}.getType());
        assertEquals(borrowBookRespons.size(), 0);
    }

    @Test
    @DisplayName("한사람의 모든 대여 기록 가져오기 성공")
    void testGetMyBorrow() throws Exception{
        //given
        doReturn(Collections.emptyList()).when(searchService).getMyReservations(any());

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/borrow/all/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final List<BorrowBookResponse> borrowBookRespons = gson.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<BorrowBookResponse>>(){}.getType());
        assertEquals(borrowBookRespons.size(), 0);
    }


    @Test
    @DisplayName("한 사람의 대여중인 대여 기록 가져오기 성공")
    void testGetMyBorrowing() throws Exception{
        //given
        doReturn(List.of(Borrow.builder().state(BorrowState.BORROWING).build()))
                .when(searchService).getMyBorrowings(1L);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/borrow/borrowing/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final List<BorrowBookResponse> borrowBookRespons = gson.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<BorrowBookResponse>>(){}.getType());
        assertEquals(borrowBookRespons.size(), 1);
        assertEquals(borrowBookRespons.get(0).getState(), BorrowState.BORROWING);
    }

    @Test
    @DisplayName("한 사람의 연체된 대여 기록 가져오기 성공")
    void testGetMyExpired() throws Exception{
        //given
        doReturn(Collections.emptyList()).when(searchService).getMyExpired(any());

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/borrow/expired/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final List<BorrowBookResponse> borrowBookRespons
                = gson.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<BorrowBookResponse>>(){}.getType());
        assertEquals(borrowBookRespons.size(), 0);

    }


    private BorrowBookResponse borrowResponseDto(){
        return BorrowBookResponse.builder()
                .state(BorrowState.BORROWING)
                .build();
    }

    private BorrowRequest borrowRequestDto(){
        return BorrowRequest.builder()
                .borrowerId(1L)
                .borrowerName("hongildong")
                .bookId(2L)
                .build();
    }
}