package com.egaz.esm.ems.clients.dto;

public record ClientRequest (
        String instituteName,
        String email,
        String phone,
        String password,
        String address,
        String role
){

}
