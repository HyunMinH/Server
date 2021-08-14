package com.example.bookreservationserver.book.controller;

import com.example.bookreservationserver.book.dto.BookDto;
import com.example.bookreservationserver.book.dto.BookRequestDto;
import com.example.bookreservationserver.book.service.BookAddService;
import com.example.bookreservationserver.book.service.BookDeleteService;
import com.example.bookreservationserver.book.service.BookSearchService;
import com.google.gson.*;
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

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookAddService bookAddService;

    @Mock
    private BookDeleteService bookDeleteService;

    @Mock
    private BookSearchService bookSearchService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    @Test
    @DisplayName("책 추가 성공")
    public void testAddBookSuccess() throws Exception {
        // given
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .book_name("책1").author("홍길동").library("본관").publisher("경북")
                .publication_date(LocalDate.now()).build();
        doNothing().when(bookAddService)
                .addBook(argThat(dto -> dto.getBook_name().equals(bookRequestDto.getBook_name())));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(bookRequestDto))
        );


        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("요청에 누락된 부분이 있어 책 추가 실패")
    public void testAddBookWithNullRequestPropertyFailed() throws Exception {
        // given
        BookRequestDto bookRequestDto1 = BookRequestDto.builder().build();
        BookRequestDto bookRequestDto2 = BookRequestDto.builder().book_name("책1")
                .author("저자1").library("별관").build();

        //when
        ResultActions resultActions1 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(bookRequestDto1))
        );
        ResultActions resultActions2 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(bookRequestDto2))
        );


        //then
        resultActions1.andExpect(status().isBadRequest());
        resultActions2.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("형식에 맞지 않은 요청 때문에 책 추가 실패")
    public void testAddBookVaildationFailed() throws Exception {
        // given
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .publisher("출판사1").book_name("책1").author("저자1")
                .library("본관").publication_date(LocalDate.now().plusDays(1)).build();

        //when
        ResultActions resultActions1 = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(bookRequestDto))
        );

        //then
        resultActions1.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("책 삭제 성공")
    public void testDeleteBookSuccess() throws Exception {
        //given
        doNothing().when(bookDeleteService).deleteBook(1L);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("책 전부 가져오기 성공")
    public void testSearchBookAllSuccess() throws Exception {
        //given
        List<BookDto> bookDtoList = bookDtoList();
        doReturn(bookDtoList).when(bookSearchService).infoBooks();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        List<BookDto> resultDtoList = gson.fromJson(mvcResult.getResponse().getContentAsString(),
                new TypeToken<List<BookDto>>(){}.getType());
        assertEquals(resultDtoList.size(), 4);
        assertEquals(resultDtoList.get(0).getBook_name(), bookDtoList.get(0).getBook_name());
    }

    @Test
    @DisplayName("한권의 책 정보 가져오기 성공")
    public void testOneUserBooksSuccess() throws Exception {
        //given
        BookDto bookDto = BookDto.builder().id(10L).build();
        doReturn(bookDto).when(bookSearchService).infoBook(10L);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/10")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        BookDto resultDto = gson.fromJson(mvcResult.getResponse().getContentAsString(), BookDto.class);
        assertEquals(resultDto.getId(), 10);
    }

    private List<BookDto> bookDtoList(){
        return List.of(
                BookDto.builder().id(1L).build(),
                BookDto.builder().id(2L).build(),
                BookDto.builder().id(3L).build(),
                BookDto.builder().id(4L).build()
        );
    }

    // gson 라이브러리 사용시
    // LocalDate type 컨버팅에 필요
    private static class LocalDateSerializer implements JsonSerializer<LocalDate>{
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(src));
        }
    }
}