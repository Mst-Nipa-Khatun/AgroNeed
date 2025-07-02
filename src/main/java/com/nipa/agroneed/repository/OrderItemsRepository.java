package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity,Long> {
    List<OrderItemsEntity> findByOrderIdAndStatus(Long orderId, Integer status);
    List<OrderItemsEntity> findByOrderId(Long orderId);

}
