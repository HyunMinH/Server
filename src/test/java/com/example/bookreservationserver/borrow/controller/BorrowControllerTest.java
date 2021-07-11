package com.example.bookreservationserver.borrow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BorrowControllerTest {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;



}