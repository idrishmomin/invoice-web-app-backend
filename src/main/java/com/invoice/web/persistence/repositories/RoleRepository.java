package com.invoice.web.persistence.repositories;

import com.invoice.web.api.enums.Roles;
import com.invoice.web.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByRoleTypeIn(List<Roles> roles);
}
