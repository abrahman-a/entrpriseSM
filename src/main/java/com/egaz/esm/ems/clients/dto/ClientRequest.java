package com.egaz.esm.ems.clients.dto;

import lombok.Data;

@Data
public class ClientRequest {
    private String instituteName;
    private String email;
    private String phone;
    private String password;
    private String address;
}
