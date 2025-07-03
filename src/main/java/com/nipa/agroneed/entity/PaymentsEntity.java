package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Payments")
public class PaymentsEntity extends BaseEntity {
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "payment_method")
    private Integer paymentMethod; //what about enum
    @Column(name = "amount")
    private Double amount;

    //status in enum

//    payment_id INT PRIMARY KEY AUTO_INCREMENT,
//    order_id INT,
//    user_id INT,
//    payment_method ENUM('PayPal', 'Stripe', 'Credit Card','Cash On'),
//    amount DECIMAL(10,2),
//    status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
//    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
}

