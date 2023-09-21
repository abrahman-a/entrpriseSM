package com.egaz.esm.ems.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="emp-info-service", url = "https://hrsample.egoz.go.tz/api/employee")
public interface EmployeeInfoApi {

    @GetMapping("/{id}")
    ResponseEntity<?> employeeInfo(@PathVariable Long id);
}
