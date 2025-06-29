package com.nipa.agroneed.dto;


public interface ShoppingCartProjection {

    public Long getId();
    public String getName();
    public Integer getQuantity();
    public Double getPrice();
    public String getDescription();
    public Long getProductId();


}
