package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class PlaceOrderDto {
    private Long userId;
    private String address;
    private String phoneNumber;
    private  Integer paymentMethod;  //When user send means cash on delivery,2 means bkash
}
