package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.ProductCommentEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductCommentEntity, Long> {
    List<ProductCommentEntity> findByProductId(Long productId);
}
