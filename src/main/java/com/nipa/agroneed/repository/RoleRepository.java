package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    long countByNameAndStatus(String roleName, Integer status);

    Role findByNameAndStatus(String roleName,Integer status);
}
