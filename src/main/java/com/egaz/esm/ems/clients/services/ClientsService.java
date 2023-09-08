package com.egaz.esm.ems.clients.services;

import com.egaz.esm.ems.clientRegister.token.VerificationToken;
import com.egaz.esm.ems.clientRegister.token.VerificationTokenRepository;
import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.dto.ClientRequest;
import com.egaz.esm.ems.clients.dto.ClientResponse;
import com.egaz.esm.ems.clients.repository.ClientsRepository;
import com.egaz.esm.ems.exception.ClientAlreadyException;
import com.egaz.esm.ems.exception.ClientNotFoundException;
import com.egaz.esm.ems.role.Role;
import com.egaz.esm.ems.role.repository.RolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientsService implements IClientService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private RolesRepository roleRepository;
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
    public Optional<Client> findById(Long id){
        return clientsRepository.findById(id);
    }
    public Client updateClient(Client request,Long clientID){
        request.setClientID(clientID);
        return clientsRepository.save(request);
    }

    @Override
    public List<ClientResponse> getClient() {
        return null;
    }

    @Override
    public Client registerClient(ClientRequest request) {
        Optional<Client> client = this.findByEmail(request.email());
        if (client.isPresent()){
            throw new ClientAlreadyException(
                    "User with email "+request.email() + " already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER").get();
        var newClient = new Client();
        newClient.setEmail(request.email());
        newClient.setPassword(passwordEncoder.encode(request.password()));
        newClient.setPhone(request.phone());
        newClient.setAddress(request.address());
        newClient.setInstituteName(request.instituteName());
        newClient.setRoles(Collections.singletonList(role));
        return clientsRepository.save(newClient);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clientsRepository.findByEmail(email);
    }

    @Override
    public void saveClientVerificationToken(Client theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        Client user = token.getClient();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        clientsRepository.save(user);
        return "valid";
    }

    @Override
    @Transactional
    public void delete(String email) {
        clientsRepository.deleteByEmail(email);
    }

    @Override
    public Client getClient(String email) {
        return clientsRepository.findByEmail(email)
                .orElseThrow(() -> new ClientNotFoundException("User not found"));
    }
    @Override
    public Client update(Client user) {
        user.setRoles(user.getRoles());
        return clientsRepository.save(user);
    }

    @Override
    public Client updateClient(Long id, Client updatedUser) {
        return null;
    }

    @Override
    public Client updatePassword(Long id, Client updatedPassword) {
        Optional<Client> existingUser = clientsRepository.findById(id);

        if (existingUser.isPresent()) {
            Client userToUpdate = existingUser.get();

            // Update the fields
            if (updatedPassword.getPassword() != null) {
                userToUpdate.setPassword(passwordEncoder.encode(updatedPassword.getPassword()));
            }
            // Save the updated password
            return clientsRepository.save(userToUpdate);
        } else {
            // Handle the case where the user with the given ID is not found
            throw new ClientNotFoundException("Password not correct");
        }
    }


}
