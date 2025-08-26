package com.nipa.agroneed.dto;

public interface ProductsbySupplierProjection {
    Long getSupplierId();

    String getProductName();

    String getDescription();

    Double getPrice();
}
