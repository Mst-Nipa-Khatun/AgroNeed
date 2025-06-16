package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.ProductsAndCategoryDetailsProjection;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.entity.CategoriesEntity;
import com.nipa.agroneed.repository.CategoriesRepository;
import com.nipa.agroneed.service.CategoriesService;
import com.nipa.agroneed.utils.ResponseBuilder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public Response createCategory(CategoriesDto categoriesDto) {
        CategoriesEntity categories = categoriesRepository.findByNameAndStatus(categoriesDto.getCategoryName(), 1);
        if (categories != null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Category already exists");
        }

        CategoriesEntity category = new CategoriesEntity();
        category.setName(categoriesDto.getCategoryName());
        category.setParentId(categoriesDto.getParentId());
        category.setStatus(1);

        if (categoriesDto.getParentId() != null) {
            CategoriesEntity parentCategory = categoriesRepository.findByIdAndStatus(categoriesDto.getParentId(), 1);
            if (parentCategory == null) {
                return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "ParentId Not Found");
            }
            category.setParentId(parentCategory.getId());
        }
        CategoriesEntity savedCategory = categoriesRepository.save(category);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, savedCategory, "Category created successfully");
    }

    @Override
    public Response getAllCategories() {
        List<CategoriesEntity> categoriesEntities = categoriesRepository.findAllByStatus(1);
        if (!categoriesEntities.isEmpty()) {

            List<CategoriesDto> categoriesDtos = new ArrayList<>();

            for (CategoriesEntity categoriesEntity : categoriesEntities) {
                CategoriesDto categoriesDto = new CategoriesDto();
                categoriesDto.setId(categoriesEntity.getId());
                categoriesDto.setCategoryName(categoriesEntity.getName());
                categoriesDto.setParentId(categoriesEntity.getParentId());
                categoriesDto.setStatus(categoriesEntity.getStatus());
                categoriesDtos.add(categoriesDto);
            }
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDtos, "Categories listed");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No categories found");
    }

    @Override
    public Response getAllParentCategories() {
        List<CategoriesEntity> categoriesEntities=categoriesRepository.findAllParentCategories();
        if(!categoriesEntities.isEmpty()){
            List<CategoriesDto> categoriesDtos=new ArrayList<>();
            for (CategoriesEntity categoriesEntity : categoriesEntities) {
                CategoriesDto categoriesDto = new CategoriesDto();
                categoriesDto.setId(categoriesEntity.getId());
                categoriesDto.setCategoryName(categoriesEntity.getName());
                categoriesDto.setParentId(categoriesEntity.getParentId());
                categoriesDto.setStatus(categoriesEntity.getStatus());
                categoriesDtos.add(categoriesDto);
            }
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDtos, " All parent Categories listed");

        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No Parent categories found");
    }

    @Override
    public Response getCategoryById(Long id) {
        CategoriesEntity categories = categoriesRepository.findByIdAndStatus(id, 1);
        if (categories != null) {
            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setCategoryName(categories.getName());
            categoriesDto.setParentId(categories.getParentId());
            categoriesDto.setStatus(categories.getStatus());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDto, "Category found");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response getCategoryByParentId(Long parentId) {
        List<CategoriesEntity> categories = categoriesRepository.findByParentIdAndStatus(parentId, 1);
        if(!categories.isEmpty()){
            List<CategoriesDto> categoriesDtos=new ArrayList<>();
            for (CategoriesEntity categoriesEntity : categories) {
                CategoriesDto categoriesDto = new CategoriesDto();
                categoriesDto.setId(categoriesEntity.getId());
                categoriesDto.setCategoryName(categoriesEntity.getName());
                categoriesDto.setParentId(categoriesEntity.getParentId());
                categoriesDto.setStatus(categoriesEntity.getStatus());
                categoriesDtos.add(categoriesDto);
            }
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoriesDtos, "Category found");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response getProductsAndCategoryByCategoryId(Long categoryId) {
        List<ProductsAndCategoryDetailsProjection> categoryDetails=categoriesRepository.findAllProductsAndCategories(categoryId);
        if(!categoryDetails.isEmpty()){
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, categoryDetails, "All Category found");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response deleteCategoryById(Long id) {
        CategoriesEntity categories = categoriesRepository.findByIdAndStatus(id, 1);
        if (categories != null) {
            categories.setStatus(0);
            CategoriesEntity deletedCategory = categoriesRepository.save(categories);

            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setCategoryName(deletedCategory.getName());
            categoriesDto.setParentId(deletedCategory.getParentId());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, deletedCategory, "Category deleted successfully");

        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }

    @Override
    public Response editCategoryById(Long id, CategoriesDto categoriesDto) {
        CategoriesEntity categories = categoriesRepository.findByIdAndStatus(id, 1);
        if (categories != null) {
            categories.setName(categoriesDto.getCategoryName());
            categories.setParentId(categoriesDto.getParentId());
            categories.setStatus(categoriesDto.getStatus());
            CategoriesEntity savedCategory = categoriesRepository.save(categories);

            CategoriesDto updatedCategoriesDto = new CategoriesDto();
            updatedCategoriesDto.setId(savedCategory.getId());
            updatedCategoriesDto.setCategoryName(savedCategory.getName());
            updatedCategoriesDto.setParentId(savedCategory.getParentId());
            updatedCategoriesDto.setStatus(savedCategory.getStatus());

            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, updatedCategoriesDto, "Category edited successfully");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No category found");
    }
}