package com.egaz.esm.ems.clients.dto;

import com.egaz.esm.ems.role.Role;
import lombok.Data;

import java.util.Set;

@Data
public class ClientResponse {
    private Long clientID;
    private String instituteName;
    private String email;
    private String phone;
    private String address;
    Set<Role> roles;
}
