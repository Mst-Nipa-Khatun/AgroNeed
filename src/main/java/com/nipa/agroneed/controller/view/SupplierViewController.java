package com.nipa.agroneed.controller.view;


import com.nipa.agroneed.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SupplierViewController {
    private final SupplierService supplierService;

    public SupplierViewController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/addSupplier")
    public String addProducts() {
        return "addSupplier";
    }

    @GetMapping("/viewAllSupplier")
    public String viewAllSuppliers() {
        return "viewAllSupplier";
    }

    @GetMapping("/supplierAllProducts")
    public String supplierAllProducts(@RequestParam("supplierId") Long supplierId, Model model) {
        model.addAttribute("supplierId", supplierId);
        return "supplierProductsDetails";
    }

    /*

        @GetMapping("/supplierSalesGenerate")
        public String supplierSalesGenerate(@RequestParam("supplierId")Long supplierId, Model model) {
            return "supplier_sales_generate";
        }
    */
    @GetMapping("/supplierSalesGenerate")
    public String supplierSalesGenerate() {
        return "supplier_sales_generate";
    }

}
