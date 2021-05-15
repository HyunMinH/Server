package com.example.bookreservationserver.qrcode.service;

import com.example.bookreservationserver.book.domain.repository.BookEntityRepository;
import com.example.bookreservationserver.qrcode.domain.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

@Service
public class BookQRCodeGenerateService {
    private QRCodeGenerator qrCodeGenerator;
    private BookEntityRepository bookEntityRepository;

    public BufferedImage generateQRCode(Long bookId) {
        if (!bookEntityRepository.existsById(bookId))
            throw new IllegalArgumentException("다음 " + bookId + " 를 id로 가지는 book이 없습니다.");

        try {
            return qrCodeGenerator.generateEAN13BarcodeImage(bookId.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Autowired
    public BookQRCodeGenerateService(QRCodeGenerator qrCodeGenerator, BookEntityRepository bookEntityRepository) {
        this.qrCodeGenerator = qrCodeGenerator;
        this.bookEntityRepository = bookEntityRepository;
    }
}