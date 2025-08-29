package com.nipa.agroneed.SSLCommerz.dto;

import com.nipa.agroneed.SSLCommerz.dto.response.InitiatePaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private boolean success;
    private String message;
    private InitiatePaymentResponse data;
}
