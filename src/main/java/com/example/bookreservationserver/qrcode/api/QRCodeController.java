package com.example.bookreservationserver.qrcode.api;

import com.example.bookreservationserver.qrcode.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequiredArgsConstructor
public class QRCodeController {
    private final QRCodeService qrCodeService;

    @GetMapping(value = "/api/qrcode/book/{bookId}", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage getBookQRCode(@PathVariable("bookId") Long bookId){
        return qrCodeService.generateQRCode(bookId);
    }
}
