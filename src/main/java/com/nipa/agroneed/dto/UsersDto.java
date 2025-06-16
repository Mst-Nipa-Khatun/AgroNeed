package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class UsersDto extends BaseDto{
    private String name;
    private String email;
    private String passwordHash;
    private String address;
    private String role;

}
