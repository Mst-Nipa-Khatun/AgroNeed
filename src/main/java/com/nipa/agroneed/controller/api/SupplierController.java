package com.nipa.agroneed.controller.api;


import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SupplierDto;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.service.SupplierService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.*;

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

    /*@PutMapping(UrlConstraint.Suppliers.UPDATE + "/{id}")
    public Response updateSupplier(@PathVariable Long id, @RequestBody SupplierDto supplierDto) {
        return supplierService.updateSupplier(id, supplierDto);
    }

    @DeleteMapping(UrlConstraint.Suppliers.DELETE + "/{id}")
    public Response deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteSupplier(id);
    }

    @GetMapping(UrlConstraint.Suppliers.GET + "/{id}")
    public Response getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }*/

}
