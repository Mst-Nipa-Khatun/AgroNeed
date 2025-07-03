package com.nipa.agroneed.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllOrderView {

    @GetMapping("/viewAllOrders")
    public String addProducts() {
        return "view_all_orders";
    }

}
