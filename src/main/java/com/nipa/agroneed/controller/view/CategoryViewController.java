package com.nipa.agroneed.controller.view;


import com.nipa.agroneed.service.CategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryViewController {
    private final CategoriesService categoriesService;

    public CategoryViewController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }
    @GetMapping("/addCategory")
    public String addCategory() {
        return "add_Category";
    }
}
