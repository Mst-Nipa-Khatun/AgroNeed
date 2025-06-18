package com.nipa.agroneed.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDto {
    private Long userId;
    private String username;
    private List<String> roles;
    private String token;
}
