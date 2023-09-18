package com.egaz.esm.ems.security.jwt;


import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JWTController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private ClientsService clientsService;

    private PasswordEncoder passwordEncoder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/login")
    public String getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest) {
        // Retrieve the user's stored password from the database based on the provided email
        Optional<Client> clientDetails = clientsService.findByEmail(authRequest.getEmail());

        // Encode the provided password using the same encoding algorithm (BCryptPasswordEncoder)
        String encodedProvidedPassword = bCryptPasswordEncoder.encode(authRequest.getPassword());

        // Compare the encoded provided password with the stored password
        if (bCryptPasswordEncoder.matches(authRequest.getPassword(), clientDetails.get().getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return "Logged in successfully";
            } else {
                return "Authentication failed: Invalid user credentials";
            }
        } else {
            return "Authentication failed: Incorrect username or password";
        }
    }

}
