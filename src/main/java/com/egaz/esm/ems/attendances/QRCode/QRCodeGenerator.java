package com.egaz.esm.ems.attendances.QRCode;

import com.egaz.esm.ems.clients.Client;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeGenerator {

    @SneakyThrows
    public static byte[] generateQRCode(String text, int width, int height) throws IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.MARGIN, 1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage qrImage = createBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);

        return baos.toByteArray();
    }


    private static BufferedImage createBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return image;
    }
//    public static void generateQRcode(Client client) throws WriteAbortedException, IOException{
//        String qrCodePath = "D:\\blog-posts\\QRcode\\";
//        String qrConeName = qrCodePath +client.getInstituteName()+client.getClientID()+"-QRCODE.png";
//        var qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(
//                "ID: "+client.getClientID()+  "\n" +
//                        "InstituteName: "+client.getInstituteName()+  "\n" +
//                        "Email: "+client.getEmail(), BarcodeFormat.QR_CODE, width:400, height:400);
//        Path path = FileSystems.getDefault().getPath(qrCodePath);
//        MatrixToImageWriter.writePath(bitMatrix, format "PNG", path);
//
//    }
}

