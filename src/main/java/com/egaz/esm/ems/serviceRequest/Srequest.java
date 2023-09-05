package com.egaz.esm.ems.serviceRequest;


import com.egaz.esm.ems.clients.Client;
import lombok.Data;

import javax.persistence.*;
//import java.sql.Blob;
import java.util.Date;

@Data
@Entity
@Table(name="request")
public class Srequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceID;
    private String serviceName;
    private String serviceDescription;
    private String serviceType;
    private String serviceStatus;
    private Date date;
    private String priority;
//    private Blob uploadFile;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


}
