package com.egaz.esm.ems.security.clientDetails;

import com.egaz.esm.ems.clients.repository.ClientsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClientRegistrationDetailsService implements UserDetailsService {
    private ClientsRepository clientsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientsRepository.findByEmail(email)
                .map(ClientRegistrationDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
