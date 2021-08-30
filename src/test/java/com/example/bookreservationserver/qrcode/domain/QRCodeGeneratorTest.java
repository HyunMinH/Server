package com.example.bookreservationserver.qrcode.domain;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeGeneratorTest {

    @Test
    @DisplayName("String으로 BufferedImage 생성 성공")
    public void testGenerateBufferedImageWithString() throws Exception {
        //given
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();

        //when
        BufferedImage bufferedImage = qrCodeGenerator.generateEAN13BarcodeImage("hello world!");

        //then
        assertEquals(readQR(bufferedImage), "hello world!");
    }


    private String readQR(BufferedImage bufferedImage) throws FileNotFoundException, IOException, NotFoundException, ChecksumException, FormatException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        return new QRCodeReader().decode(binaryBitmap).getText();
    }
}