package com.egaz.esm.ems.security.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}
