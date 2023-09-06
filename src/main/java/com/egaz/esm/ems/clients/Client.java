package com.egaz.esm.ems.clients;

import com.egaz.esm.ems.role.Role;
import com.egaz.esm.ems.serviceRequest.Srequest;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
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


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "client_roles",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "clientID"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();
}
