package com.egaz.esm.ems.role.service;


import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.role.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role createRole(Role theRole);
    void deleteRole(Long roleId);
    Role findByName(String name);
    Role findById(Long roleId);
    Client removeUserFromRole(Long clientID, Long roleId);
    Client assignUserToRole(Long clientID, Long roleId);
    Role removeAllUserFromRole(Long roleId);
}
