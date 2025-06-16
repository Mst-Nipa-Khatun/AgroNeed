package com.nipa.agroneed.controller.api;


import com.nipa.agroneed.dto.CategoriesDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.service.CategoriesService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstraint.Categories.ROOT)
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(UrlConstraint.Categories.CREATE)
    public Response createCategory(@RequestBody CategoriesDto categoriesDto) {
        return categoriesService.createCategory(categoriesDto);
    }
    @GetMapping(UrlConstraint.Categories.GET_ALL)
    public Response getAllCategories() {
        return categoriesService.getAllCategories();
    }
    @GetMapping(UrlConstraint.Categories.GET_ALL_PARENT_CATEGORY)
    public Response getAllParentCategories() {
        return categoriesService.getAllParentCategories();
    }

    @GetMapping(UrlConstraint.Categories.GET_BY_ID)
    public Response getCategoryById(@PathVariable("id") Long id) {
        return categoriesService.getCategoryById(id);
    }
    @GetMapping(UrlConstraint.Categories.GET_BY_ParentId)
    public Response getCategoryByParentId(@PathVariable("parentId") Long parentId) {
        return categoriesService.getCategoryByParentId(parentId);
    }
   @GetMapping(UrlConstraint.Categories.GET_PRODUCTS_AND_CATEGORY_BY_CATEGORY_ID)
   public Response getProductsAndCategoryByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return categoriesService.getProductsAndCategoryByCategoryId(categoryId);
   }
    @DeleteMapping(UrlConstraint.Categories.DELETE_BY_ID)
    public Response deleteCategoryById(@PathVariable("id") Long id) {
        return categoriesService.deleteCategoryById(id);
    }
    @PutMapping(UrlConstraint.Categories.EDIT_BY_ID)
    public Response editCategoryById(@PathVariable("id") Long id, @RequestBody CategoriesDto categoriesDto) {
        return categoriesService.editCategoryById(id,categoriesDto);
    }
}
