package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.ProductCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsCategoriesRepository extends JpaRepository<ProductCategoriesEntity,Long> {
}
