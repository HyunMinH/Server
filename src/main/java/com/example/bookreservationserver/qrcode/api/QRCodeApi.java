package com.example.bookreservationserver.qrcode.api;

import com.example.bookreservationserver.qrcode.service.BookQRCodeGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
public class QRCodeApi {
    private BookQRCodeGenerateService bookQrCodeGenerateService;

    @GetMapping(value = "/api/qrcode/book/{bookId}", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage getBookQRCode(@PathVariable("bookId") Long bookId){
        return bookQrCodeGenerateService.generateQRCode(bookId);
    }

    @Autowired
    public QRCodeApi(BookQRCodeGenerateService bookQrCodeGenerateService) {
        this.bookQrCodeGenerateService = bookQrCodeGenerateService;
    }
}
