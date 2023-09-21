package com.egaz.esm.ems.attendances.dto;

import lombok.Data;

@Data
public record EmployeeDto(
        Long id,
        String firstName,
        String secondName,
        String lastName,

        String birthDate,

        String gender,

        String nationality,

        String email,

        String address,

        String photo,

        String maritalStatus,

        String position,
        String employmentType,

        String dateHired,
        String department,
        String employmentStatus,
        String workLocation,
        String salaryScale
        ) {
}
