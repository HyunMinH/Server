package com.example.bookreservationserver.qrcode.api;

import com.example.bookreservationserver.advice.GlobalExceptionHandler;
import com.example.bookreservationserver.qrcode.domain.QRCodeGenerator;
import com.example.bookreservationserver.qrcode.service.QRCodeService;
import com.example.bookreservationserver.skeleton.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class QRCodeControllerTest extends ControllerTest {
    @InjectMocks
    private QRCodeController qrCodeController;

    @Mock
    private QRCodeService qrCodeService;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
                .setMessageConverters(new BufferedImageHttpMessageConverter())
                .setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    @DisplayName("QRCode를 책 id로 생성 성공")
    void testQRCodeApiSucess() throws Exception {
        //given
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        doReturn(qrCodeGenerator.generateEAN13BarcodeImage("hello world!")).when(qrCodeService).generateQRCode(any());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/qrcode/book/1")
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsByteArray());
    }

    @Override
    protected Object getController() {
        return qrCodeController;
    }
}