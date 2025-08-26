package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    SupplierEntity findByNameAndStatus(String name, Integer status);

    Optional<SupplierEntity> findByIdAndStatus(Long id, Integer status);
}
