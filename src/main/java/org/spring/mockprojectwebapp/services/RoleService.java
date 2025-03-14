package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(int id);
    Role save(Role role);
    Role update(int id, Role role);

}
