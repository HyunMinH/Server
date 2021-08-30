package com.example.bookreservationserver.qrcode.service;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.qrcode.domain.QRCodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QRCodeServiceTest {
    @InjectMocks
    private QRCodeService qrCodeService;

    @Mock
    private QRCodeGenerator qrCodeGenerator;

    @Mock
    private BookRepository bookRepository;

    @Test
    void testGenerateQRCodeSuccess() throws Exception {
        //given
        doReturn(Optional.of(Book.builder().build())).when(bookRepository).findById(any());
        doReturn(new QRCodeGenerator().generateEAN13BarcodeImage("hello")).when(qrCodeGenerator).generateEAN13BarcodeImage(any());

        //when
        BufferedImage bufferedImage = qrCodeService.generateQRCode(1L);

        //then
        assertNotNull(bufferedImage);
    }

    @Test
    void testGenerateQRCodeFailed() throws Exception {
        //given
        doReturn(Optional.empty()).when(bookRepository).findById(1L);
        doReturn(Optional.of(Book.builder().build())).when(bookRepository).findById(2L);
        doReturn(new QRCodeGenerator().generateEAN13BarcodeImage("hello")).when(qrCodeGenerator).generateEAN13BarcodeImage("1L");

        //when
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, ()->qrCodeService.generateQRCode(1L));
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, ()->qrCodeService.generateQRCode(2L));

        //then
        assertEquals(exception1.getMessage(), "해당하는 책이 없습니다.");
        assertEquals(exception2.getMessage(), "QRCode로 변환할 수 없습니다.");
    }
}