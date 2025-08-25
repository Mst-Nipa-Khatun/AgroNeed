package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class SupplierDto extends BaseDto{
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer status;
}
