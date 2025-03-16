package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role getRoleByRoleId(int id);
}
