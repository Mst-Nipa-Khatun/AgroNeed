package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SupplierDto;
import com.nipa.agroneed.entity.SupplierEntity;
import com.nipa.agroneed.repository.SupplierRepository;
import com.nipa.agroneed.service.SupplierService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Response addSupplier(SupplierDto supplierDto) {
        SupplierEntity suppliersExits = supplierRepository.findByNameAndStatus(supplierDto.getName(), 1);
        if (suppliersExits != null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Supplier already exists");

        }
        SupplierEntity supplier = new SupplierEntity();
        supplier.setName(supplierDto.getName());
        supplier.setStatus(1);
        supplier.setEmail(supplierDto.getEmail());
        supplier.setPhone(supplierDto.getPhone());
        supplier.setAddress(supplierDto.getAddress());
        SupplierEntity addsupplier = supplierRepository.save(supplier);


        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, addsupplier, "Supplier added successfully");
    }

    @Override
    public Response getAllSuppliers() {
        List<SupplierEntity> suppliers = supplierRepository.findAll();
        if (!suppliers.isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, suppliers, "Supplier list");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, null, "Supplier not found");
    }

    /*@Override
    public Response updateSupplier(Long id, SupplierDto supplierDto) {
        Optional<SupplierEntity> supplierOpt = supplierRepository.findById(id);
        if (supplierOpt.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, null, "Supplier not found");
        }

        SupplierEntity supplier = supplierOpt.get();
        supplier.setName(supplierDto.getName());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setPhone(supplierDto.getPhone());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setStatus(supplierDto.getStatus());

        SupplierEntity updated = supplierRepository.save(supplier);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, updated, "Supplier updated successfully");
    }

    @Override
    public Response deleteSupplier(Long id) {
        Optional<SupplierEntity> supplierOpt = supplierRepository.findById(id);
        if (supplierOpt.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, null, "Supplier not found");
        }

        SupplierEntity supplier = supplierOpt.get();
        supplier.setStatus(0); // soft delete
        supplierRepository.save(supplier);

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, "Supplier deleted successfully");
    }

    @Override
    public Response getSupplierById(Long id) {
        Optional<SupplierEntity> supplierOpt = supplierRepository.findById(id);
        if (supplierOpt.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, null, "Supplier not found");
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, supplierOpt.get(), "Supplier fetched successfully");
    }*/
}
