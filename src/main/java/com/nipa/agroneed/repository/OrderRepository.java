package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
    //OrdersEntity findByUserIdAndStatus(Long userId, Integer status);
    List<OrdersEntity> findAllByStatus(Integer status);

   OrdersEntity findByIdAndStatus(Long id,Integer status);

   OrdersEntity findByTransactionIdAndStatus(String transactionId,Integer status);
}
