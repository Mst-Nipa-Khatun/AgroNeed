package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class ShoppingCartDto extends BaseDto{
    private Long userId;
    private Long productId;
    private Integer quantity;
}
