package com.egaz.esm.ems.role;


import com.egaz.esm.ems.clients.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<Client> clients = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void removeAllUsersFromRole(){
        if (this.getClients() != null){
            List<Client> usersInRole = this.getClients().stream().toList();
            usersInRole.forEach(this::removeUserFromRole);
        }
    }
    public void removeUserFromRole(Client client) {
        client.getRoles().remove(this);
        this.getClients().remove(client);
    }
    public void assignUserToRole(Client client){
        client.getRoles().add(this);
        this.getClients().add(client);
    }

}
