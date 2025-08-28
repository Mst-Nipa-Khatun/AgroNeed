package com.nipa.agroneed.controller.api;


import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SupplierDto;
import com.nipa.agroneed.dto.SupplierSalesProjection;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.service.SupplierService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlConstraint.Suppliers.ROOT)
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping(UrlConstraint.Suppliers.CREATE)
    public Response createSupplier(@RequestBody SupplierDto supplierDto) {
        return supplierService.addSupplier(supplierDto);
    }
    @GetMapping(UrlConstraint.Suppliers.GET_ALL)
    public Response getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping(UrlConstraint.Suppliers.SUPPLIER_SALES_GENERATE)
    public Response supplierSalesGenerate() {
        return supplierService.supplierSalesGenerate();
    }


}
