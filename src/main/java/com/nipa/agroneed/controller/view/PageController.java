package com.nipa.agroneed.controller.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.service.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pages")
public class PageController {
    private static final Logger log = LoggerFactory.getLogger(PageController.class);
    private final CategoriesService categoriesService;

    public PageController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/dashboard")
    public String index() {
        return "dashboard";
    }

    @GetMapping("/currentProduct")
    public String viewCurrentProduct(@RequestParam Long categoryId, Model model) throws JsonProcessingException {
        Response response = categoriesService.getProductsAndCategoryByCategoryId(categoryId);
        log.info(new ObjectMapper().writeValueAsString(response.getContent()));
        model.addAttribute("products", response.getContent());
        return "current_product";
    }
    @GetMapping("/success-order-conformation")
    public String viewSuccessOrderConformation(Model model) {
        return "success_order";
    }
}
