package com.nipa.agroneed.SSLCommerz.service;

import com.nipa.agroneed.SSLCommerz.config.SSLCommerzConfig;
import com.nipa.agroneed.SSLCommerz.dto.PaymentRequest;
import com.nipa.agroneed.SSLCommerz.dto.response.InitiatePaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PaymentService {

    @Value("${server.port}")
    private Integer port;

    private final SSLCommerzConfig config;


    public InitiatePaymentResponse initiatePayment(PaymentRequest request, String transactionId) {

        Map<String, String> params = new HashMap<>();
        params.put("store_id", config.getStoreId());
        params.put("store_passwd", config.getStorePassword());
        params.put("total_amount", request.getAmount());
        params.put("currency", request.getCurrency());
        params.put("tran_id", transactionId);
        params.put("success_url", "http://localhost:" + port + "/ssl-commerz/payment/success");
        params.put("fail_url", "http://localhost:" + port + "/ssl-commerz/payment/fail");
        params.put("cancel_url", "http://localhost:" + port + "/ssl-commerz/payment/cancel");
        params.put("ipn_url", "http://localhost:" + port + "/ssl-commerz/payment/ipn");

        // Customer info
        params.put("cus_name", request.getCustomerName());
        params.put("cus_email", request.getCustomerEmail());
        params.put("cus_add1", request.getCustomerAddress1());
        params.put("cus_city", request.getCustomerCity());
        params.put("cus_postcode", request.getCustomerPostcode());
        params.put("cus_country", request.getCustomerCountry());
        params.put("cus_phone", request.getCustomerPhone());

        // Product info
        params.put("product_name", request.getProductName());
        params.put("product_category", request.getProductCategory());
        params.put("product_profile", "general");
        params.put("shipping_method", "NO");

        //  Convert params to Form URL Encoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        StringBuilder bodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            bodyBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        String requestBody = bodyBuilder.substring(0, bodyBuilder.length() - 1);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        //  Call SSLCommerz API
        String apiUrl = config.getUrl();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<InitiatePaymentResponse> response =
                restTemplate.exchange(apiUrl, HttpMethod.POST, entity, InitiatePaymentResponse.class);

        return response.getBody();
    }


}
