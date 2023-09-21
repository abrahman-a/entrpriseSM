package com.egaz.esm.ems.attendances;

import com.egaz.esm.ems.attendances.statusEnum.AttendanceStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name ="attendances")
public class EmployeeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
    private AttendanceStatus status;
}
