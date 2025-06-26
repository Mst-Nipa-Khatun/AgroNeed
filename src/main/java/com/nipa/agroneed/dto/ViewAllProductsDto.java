package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class ViewAllProductsDto {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private Integer status;
    private Long categoryId;
    private Long productId;
}
