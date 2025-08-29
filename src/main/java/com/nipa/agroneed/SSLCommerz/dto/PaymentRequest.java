package com.nipa.agroneed.SSLCommerz.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String amount;
    private String currency = "BDT";
    private String customerName;
    private String customerEmail;
    private String customerAddress1;
    private String customerCity;
    private String customerPostcode;
    private String customerCountry;
    private String customerPhone;
    private String productCategory ;
    private String productName;
}