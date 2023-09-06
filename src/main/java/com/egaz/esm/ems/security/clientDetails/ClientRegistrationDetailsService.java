package com.egaz.esm.ems.security.clientDetails;

import com.egaz.esm.ems.clients.repository.ClientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientRegistrationDetailsService implements UserDetailsService {
    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientsRepository.findByEmail(email)
                .map(ClientRegistrationDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
