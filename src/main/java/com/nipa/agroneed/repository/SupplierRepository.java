package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    SupplierEntity findByNameAndStatus(String name, Integer status);


}
