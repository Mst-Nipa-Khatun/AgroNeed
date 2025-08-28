package com.nipa.agroneed.dto;

import java.math.BigDecimal;

public interface SupplierSalesProjection {
    String getSupplierName();
    String getProductName();
    BigDecimal getSupplierSales();
    BigDecimal getTotalQuantitySold();
}
