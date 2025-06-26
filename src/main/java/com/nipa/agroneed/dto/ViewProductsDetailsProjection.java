package com.nipa.agroneed.dto;

public interface ViewProductsDetailsProjection {
    Long getProductId();

    String getName();

    String getDescription();

    Double getPrice();

    Integer getStock();

    String getImageUrl();

    Integer getStatus();

    Long getCategoryId();
}
