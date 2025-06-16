package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class ProductsEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;


//    product_id INT PRIMARY KEY AUTO_INCREMENT,
//    name VARCHAR(255) NOT NULL,
//    description TEXT,
//    price DECIMAL(10,2) NOT NULL,
//    stock INT NOT NULL,
//    category VARCHAR(255),
//    image_url VARCHAR(255),
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

}
