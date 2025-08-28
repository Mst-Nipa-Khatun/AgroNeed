package com.nipa.agroneed.repository;

import com.nipa.agroneed.dto.SupplierSalesProjection;
import com.nipa.agroneed.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    SupplierEntity findByNameAndStatus(String name, Integer status);

    Optional<SupplierEntity> findByIdAndStatus(Long id, Integer status);

    @Query(value = """
        SELECT
            s.name AS supplierName,
            p.name AS productName,
            COALESCE(SUM(oi.quantity * oi.price), 0) AS supplierSales,
            COALESCE(SUM(oi.quantity), 0)         AS totalQuantitySold
        FROM Suppliers s
        JOIN Supplier_Products sp ON sp.supplier_id = s.id
        JOIN Products p           ON p.id = sp.product_id
        LEFT JOIN Order_Items oi  ON oi.product_id = p.id
        LEFT JOIN Orders o        ON o.id = oi.order_id
        GROUP BY s.id, s.name, p.id, p.name
        ORDER BY s.name, p.name
        """, nativeQuery = true)
    List<SupplierSalesProjection> supplierSalesGenerate();
}
