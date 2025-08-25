package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "Supplier_Products")
public class SupplierProductEntity extends BaseEntity{
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;
}
