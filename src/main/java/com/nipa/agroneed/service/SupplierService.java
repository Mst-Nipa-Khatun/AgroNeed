package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SupplierDto;
import com.nipa.agroneed.dto.SupplierSalesProjection;

import java.util.List;

public interface SupplierService {

    Response addSupplier(SupplierDto supplierDto);
    Response getAllSuppliers();
  Response supplierSalesGenerate();


    /* Response updateSupplier(Long id, SupplierDto supplierDto);
    Response deleteSupplier(Long id);
    Response getSupplierById(Long id);*/
}
