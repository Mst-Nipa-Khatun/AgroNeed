package com.nipa.agroneed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // 👈 Important
@Data
@Entity
@Table(name = "Supplier_Products")
public class SupplierProductEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;
}
