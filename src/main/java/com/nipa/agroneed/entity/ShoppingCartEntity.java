package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Shopping_Cart")
public class ShoppingCartEntity extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "quantity")
    private Integer quantity;

//    cart_id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT,
//    product_id INT,
//    quantity INT,
//    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
//    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
}
