package com.egaz.esm.ems.attendances.QRCode;

import com.egaz.esm.ems.attendances.EmployeeAttendance;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

//@Service
public class QRCodeGenerator {

    public static byte[] generateQRcode(EmployeeAttendance employeeAttendance) throws WriterException, IOException {
        int width = 400;
        int height = 400;

        String qrCodePath = "/home/abdul-wiseboy/Downloads/entrpriseSM/QRcode";
        String qrCodeName = qrCodePath + employeeAttendance.getFirstName() + "-" + employeeAttendance.getId() + "-QRCODE.png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID: " + employeeAttendance.getId() + "\n" +
                        "InstituteName: " + employeeAttendance.getFirstName() + "\n" +
                        "Email: " + employeeAttendance.getEmailAddress() + "\n" +
                        "Time in: " + employeeAttendance.getSignInTime() + "\n" +
                        "Time out: " + employeeAttendance.getSignOutTime() + "\n" +
                        "Status: " + employeeAttendance.getStatus(),
                BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        // You can return the byte array of the generated image if needed
        return new byte[0];
    }
}

