package com.nipa.agroneed.repository;


import com.nipa.agroneed.dto.ProductsAndCategoryDetailsProjection;
import com.nipa.agroneed.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<CategoriesEntity,Long> {

    CategoriesEntity findByNameAndStatus(String name,Integer status);
    CategoriesEntity findByIdAndStatus(Long id,Integer status);
    List<CategoriesEntity> findAllByStatus(Integer status);

    @Query(value = "Select *from categories where parent_id is null",nativeQuery = true)
    List<CategoriesEntity> findAllParentCategories();


    List<CategoriesEntity> findByParentIdAndStatus(Long parentId, Integer status);

    @Query(value = "select c.name as categoryName,\n" +
            "       p.name as productName,\n" +
            "       p.description as description,\n" +
            "       p.price,\n" +
            "       p.image_url as imageUrl,\n" +
            "       p.stock,\n" +
            "       p.status,\n" +
            "       pc.product_id\n" +
            "from products p\n" +
            "         join product_categories pc on p.id = pc.product_id\n" +
            "         join categories c on c.id = pc.category_id\n" +
            "where pc.category_id = :category_id",nativeQuery = true)
    List<ProductsAndCategoryDetailsProjection> findAllProductsAndCategories(@Param("category_id") Long categoryId);
}
