package com.egaz.esm.ems.attendances.service;



import com.egaz.esm.ems.attendances.EmployeeAttendance;
import com.egaz.esm.ems.attendances.EmployeeInfo;
import com.egaz.esm.ems.attendances.QRCode.QRCodeGenerator;
import com.egaz.esm.ems.attendances.dto.EmployeeDto;
import com.egaz.esm.ems.attendances.repository.EmployeeAttendanceRepository;
import com.egaz.esm.ems.attendances.statusEnum.AttendanceStatus;
import com.google.zxing.WriterException;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.channels.WritePendingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private EmployeeAttendanceRepository employeeAttendanceRepository;

    public List<EmployeeAttendance> getEmp(){
        return employeeAttendanceRepository.findAll();}

}


