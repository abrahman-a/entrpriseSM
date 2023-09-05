package com.egaz.esm.ems.clients.services;

import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.dto.ClientRequest;
import com.egaz.esm.ems.clients.dto.ClientResponse;
import com.egaz.esm.ems.clients.repository.ClientsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepository  clientsRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Client addClient(ClientRequest request){
        Client s = modelMapper.map(request,Client.class);
        return clientsRepository.save(s);
    }
    public List<Client> getAllClient(){
        return clientsRepository.findAll();
    }
    public Optional<Client> findByEmail(String email) {
        return clientsRepository.findByEmail(email);
    }
    public Optional<Client> findById(Long id){
        return clientsRepository.findById(id);
    }
    public Client updateClient(Client request,Long clientID){
        request.setClientID(clientID);
        return clientsRepository.save(request);
    }

}
