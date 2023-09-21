package com.egaz.esm.ems.attendances;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class EmployeeInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String employeeId;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String phoneNumber;
        private LocalDate dateOfBirth;
        private String department;
        private String position;
        private LocalDate hireDate;
}
