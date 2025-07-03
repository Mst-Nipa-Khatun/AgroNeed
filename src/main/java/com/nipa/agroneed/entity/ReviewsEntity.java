package com.nipa.agroneed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Reviews")
public class ReviewsEntity extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "comment")
    private String comment;

//    review_id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT,
//    product_id INT,
//    rating INT CHECK (rating BETWEEN 1 AND 5),
//    comment TEXT,
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
//    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
}
