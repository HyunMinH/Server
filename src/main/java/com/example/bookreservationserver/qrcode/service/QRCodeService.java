package com.example.bookreservationserver.qrcode.service;

import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.qrcode.domain.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class QRCodeService {
    private final QRCodeGenerator qrCodeGenerator;
    private final BookRepository bookRepository;

    public BufferedImage generateQRCode(Long bookId) {
        if (!bookRepository.existsById(bookId))
            throw new IllegalArgumentException("다음 " + bookId + " 를 id로 가지는 book이 없습니다.");

        try {
            return qrCodeGenerator.generateEAN13BarcodeImage(bookId.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}