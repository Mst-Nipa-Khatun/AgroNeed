package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_categories")
public class ProductCategoriesEntity extends BaseEntity{
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "category_id")
    private Long categoryId;


//    id INT PRIMARY KEY AUTO_INCREMENT,
//    product_id INT NOT NULL,
//    category_id INT NOT NULL,
//    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
//    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
}
