package com.nipa.agroneed.controller.view;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCartInfoController {

    @GetMapping("/shoppingCart")
    public String shoppingCart() {
        return "shopping_cart";
    }

}
