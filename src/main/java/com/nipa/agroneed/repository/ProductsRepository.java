package com.nipa.agroneed.repository;

import com.nipa.agroneed.dto.ViewProductsDetailsProjection;
import com.nipa.agroneed.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity,Long> {
    ProductsEntity findByNameAndStatus(String name, Integer status);
    ProductsEntity findByIdAndStatus(Long id, Integer status);


   // List<ProductsEntity> findAllByStatus(Integer status);

    @Query(value = "select p.id as productId,\n" +
            "       p.name as Name,\n" +
            "       p.description as Description,\n" +
            "       p.price as Price,\n" +
            "       p.stock as Stock,\n" +
            "       p.image_url as ImageUrl,\n" +
            "       p.status as Status,\n" +
            "       pc.category_id as CategoryId\n" +
            "from products p\n" +
            "         join product_categories pc ON p.id = pc.product_id", nativeQuery = true)
    List<ViewProductsDetailsProjection> findByAllProducts();
}
