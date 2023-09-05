package com.egaz.esm.ems.clients.dto;

import lombok.Data;

@Data
public class ClientResponse {
    private Long clientID;
    private String instituteName;
    private String email;
    private String phone;
    private String address;
}
