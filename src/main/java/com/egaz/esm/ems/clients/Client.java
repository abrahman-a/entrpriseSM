package com.egaz.esm.ems.clients;

import com.egaz.esm.ems.serviceRequest.Srequest;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name ="Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long clientID;
    private String instituteName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String phone;
    private String address;
}
