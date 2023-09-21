//package com.egaz.esm.ems.attendances;
//
//
//import com.egaz.esm.ems.apis.EmployeeInfoApi;
//import com.egaz.esm.ems.attendances.dto.EmployeeDto;
//import com.egaz.esm.ems.attendances.service.AttendanceService;
//import com.egaz.esm.ems.attendances.QRCode.QRCodeGenerator;
//import com.google.zxing.WriterException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@Controller
//@RestController
//@RequestMapping("/api/test")
//public class AttendanceController {
//    @Autowired
//    private EmployeeInfoApi employeeInfoApi;
//    @Autowired
//    private AttendanceService attendanceService;
//    @Autowired
//    private QRCodeGenerator qrCodeGenerator;
//
//    @GetMapping("/emp-inf")
//    public ResponseEntity<List<EmployeeAttendance>> getEmpInf() {
//        List<EmployeeAttendance> employeeAttendances = attendanceService.getEmp();
//        if (!employeeAttendances.isEmpty()) {
//            for (EmployeeAttendance employeeAttendance : employeeAttendances) {
//                byte[] qrCodeImage = qrCodeGenerator.generateQRcode(employeeAttendance);
//                // You can now do something with the qrCodeImage, such as saving it to a file or returning it as a response
//            }
//        }
//        ResponseEntity<List<EmployeeAttendance>> responseEntity = employeeInfoApi.employeeInfo(10001L);
//        return responseEntity;
//    }
//}
//
