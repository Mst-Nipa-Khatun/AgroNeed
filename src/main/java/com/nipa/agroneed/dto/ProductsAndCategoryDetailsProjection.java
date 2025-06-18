package com.nipa.agroneed.dto;

public interface ProductsAndCategoryDetailsProjection {
    public String getCategoryName();
    public String getProductName();
    public String getDescription();
    public String getImageUrl();
    public Double getPrice();
    public Integer getStock();
    public String getStatus();
    public Long getProductId();

}
