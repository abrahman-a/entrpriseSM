package com.egaz.esm.ems.clients.services;

import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.dto.ClientRequest;
import com.egaz.esm.ems.clients.dto.ClientResponse;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<ClientResponse> getClient();

    Client registerClient(ClientRequest request);
    Optional<Client> findByEmail(String email);

    void saveClientVerificationToken(Client theClient, String verificationToken);

    String validateToken(String theToken);

    void delete(String email);
    Client getClient(String email);
    Client update(Client client);
    Client updateClient(Long id, Client updatedUser);

    Client updatePassword(Long id, Client updatedPassword);
}
