package com.egaz.esm.ems.security.clientDetails;

import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.repository.ClientsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientRegistrationDetails implements UserDetails {

    @Autowired
    private ClientsRepository clientsRepository;

    private String userName;
    private String password;
    private boolean isEnabled;
    private boolean notLocked;
    private List<GrantedAuthority> authorities;
    public ClientRegistrationDetails(Client client) {
        this.userName = client.getEmail();
        this.password = client.getPassword();
        this.isEnabled = client.isEnabled();
        this.notLocked = client.isNotLocked();
        this.authorities = Arrays.stream(client.getRoles().toString()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return notLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}

