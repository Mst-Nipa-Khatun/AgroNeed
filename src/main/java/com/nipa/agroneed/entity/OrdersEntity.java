package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class OrdersEntity extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "numbers_of_products")
    private Integer numberOfProducts;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private  String phoneNumber;

    @Column(name = "payment_method")
    private  Integer paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "bank_tran_id")
    private String bankTransactionId;

    @Column(name = "transaction_status")
    private Integer transactionStatus;

    @Column(name = "failed_transaction_reason")
    private String failedTransactionReason;


    //status in enum

//    order_id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT,
//    total_price DECIMAL(10,2),
//    status ENUM('pending', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
}
