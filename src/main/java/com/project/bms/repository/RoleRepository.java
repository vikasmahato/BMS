package com.project.bms.repository;

import com.project.bms.model.Role;
import com.project.bms.model.RoleName;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    Boolean existsByName(RoleName roleName);
}