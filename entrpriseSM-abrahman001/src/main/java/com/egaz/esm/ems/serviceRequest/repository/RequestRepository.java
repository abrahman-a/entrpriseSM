package com.egaz.esm.ems.serviceRequest.repository;

import com.egaz.esm.ems.serviceRequest.Srequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository <Srequest, Long>{

    ///
}
