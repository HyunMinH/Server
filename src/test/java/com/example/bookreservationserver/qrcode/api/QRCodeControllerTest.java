package com.example.bookreservationserver.qrcode.api;

import com.example.bookreservationserver.qrcode.service.QRCodeService;
import com.example.bookreservationserver.skeleton.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class QRCodeControllerTest extends ControllerTest {
    @InjectMocks
    private QRCodeController qrCodeController;

    @Mock
    private QRCodeService qrCodeService;


    @Test
    @DisplayName("QRCode를 책 id로 생성 성공")
    void testQRCodeApiSucess() {
        // given
    }

    @Override
    protected Object getController() {
        return qrCodeController;
    }
}