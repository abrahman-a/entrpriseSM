package com.egaz.esm.ems.attendances.QRCode;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/test")
public class QRCodeController {
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @GetMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)

    public byte[] generateQRCode(@RequestParam String text,
                                 @RequestParam int width,
                                 @RequestParam int height) throws IOException {
        return QRCodeGenerator.generateQRCode(text, width, height);
    }
}
