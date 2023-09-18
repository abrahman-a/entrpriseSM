package com.egaz.esm.ems.role.repository;

import com.egaz.esm.ems.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
