package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class UpdateOrderDto {
    private Long orderId;
    private Long userId;
    private Integer orderStatus;
}
