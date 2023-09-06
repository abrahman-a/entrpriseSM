package com.egaz.esm.ems.role.service;

import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.repository.ClientsRepository;
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
            throw new RoleAlreadyExistException(checkRole.get().getName()+ " role already exist");
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
        Optional<Client> user = clientsRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getClients().contains(user.get())) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UserNotFoundException("User not found in the specified role!!");
    }

    @Override
    public Client assignUserToRole(Long userId, Long roleId) {
        Optional<Client> user = clientsRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getInstituteName()+ " is already assigned to the " + role.get().getName() +" role");
        }
        role.ifPresent(theRole -> theRole.assignClientToRole(client.get()));
        roleRepository.save(role.get());
        return user.get();
    }

    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        roleRepository.save(role.get());
        return role.get();
    }
}
