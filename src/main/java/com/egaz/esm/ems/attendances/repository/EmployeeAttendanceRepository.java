package com.egaz.esm.ems.attendances.repository;

import com.egaz.esm.ems.attendances.EmployeeAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {

    //////
}
