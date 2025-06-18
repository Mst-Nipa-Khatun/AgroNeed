package com.nipa.agroneed.controller.view;

import com.nipa.agroneed.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {
    private final ProductsService productsService;


    public ProductViewController(ProductsService productsService) {
        this.productsService = productsService;
    }
    @GetMapping("/addProducts")
    public String addProducts() {
        return "add_Products";
    }

    @GetMapping("/viewAllProducts")
    public String viewAllProducts() {
        return "view_Products";
    }
}
