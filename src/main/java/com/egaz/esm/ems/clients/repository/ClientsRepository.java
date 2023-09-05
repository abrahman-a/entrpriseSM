package com.egaz.esm.ems.clients.repository;

import com.egaz.esm.ems.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientsRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
