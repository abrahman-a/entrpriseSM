package com.egaz.esm.ems.role.service;

import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.repository.ClientsRepository;
import com.egaz.esm.ems.exception.ClientAlreadyException;
import com.egaz.esm.ems.exception.ClientNotFoundException;
import com.egaz.esm.ems.exception.RoleAlreadyException;
import com.egaz.esm.ems.role.Role;
import com.egaz.esm.ems.role.repository.RolesRepository;

import java.util.List;
import java.util.Optional;

public class RoleService implements IRoleService {

    private  RolesRepository roleRepository;
    private  ClientsRepository clientsRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        Optional<Role> checkRole = roleRepository.findByName(theRole.getName());
        if (checkRole.isPresent()){
            throw new RoleAlreadyException(checkRole.get().getName()+ " role already exist");
        }
        return roleRepository.save(theRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUserFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }
    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Override
    public Client removeUserFromRole(Long userId, Long roleId) {
        Optional<Client> client = clientsRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getClients().contains(client.get())) {
            role.get().removeUserFromRole(client.get());
            roleRepository.save(role.get());
            return client.get();
        }
        throw new ClientNotFoundException("User not found in the specified role!!");
    }

    @Override
    public Client assignUserToRole(Long userId, Long roleId) {
        Optional<Client> client = clientsRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (client.isPresent() && client.get().getRoles().contains(role.get())){
            throw new ClientAlreadyException(
                    client.get().getInstituteName()+ " is already assigned to the " + role.get().getName() +" role");
        }
        role.ifPresent(theRole -> theRole.assignUserToRole(client.get()));
        roleRepository.save(role.get());
        return client.get();
    }

    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        roleRepository.save(role.get());
        return role.get();
    }
}
