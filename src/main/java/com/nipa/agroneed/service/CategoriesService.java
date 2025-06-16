package com.nipa.agroneed.service;


import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.Response;

public interface CategoriesService {
    Response createCategory(CategoriesDto categoriesDto);
    Response getAllCategories();
    Response getAllParentCategories();
    Response getCategoryById(Long id);
    Response getCategoryByParentId(Long parentId);
    Response getProductsAndCategoryByCategoryId(Long categoryId);
    Response deleteCategoryById(Long id);
    Response editCategoryById(Long id, CategoriesDto categoriesDto);
}
