package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class IncrementDecrementShoppingCartDto {
    private Long productId;
    private Long userId;
    private Boolean isIncrement;
}
