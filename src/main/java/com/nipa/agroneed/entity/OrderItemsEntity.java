package com.nipa.agroneed.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "Order_Items")
public class OrderItemsEntity extends BaseEntity {

    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;

//    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
//    order_id INT,
//    product_id INT,
//    quantity INT,
//    price DECIMAL(10,2),
//    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
//    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
}
