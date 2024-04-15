package com.service.auth.repository;

import com.service.auth.entitys.Role;
import com.service.auth.enums.RoleSystem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleSystem name);
    boolean existsByName(RoleSystem roleSystem);
}
