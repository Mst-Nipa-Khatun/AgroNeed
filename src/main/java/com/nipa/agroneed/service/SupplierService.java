package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SupplierDto;

public interface SupplierService {

    Response addSupplier(SupplierDto supplierDto);
    Response getAllSuppliers();

    /* Response updateSupplier(Long id, SupplierDto supplierDto);
    Response deleteSupplier(Long id);
    Response getSupplierById(Long id);*/
}
