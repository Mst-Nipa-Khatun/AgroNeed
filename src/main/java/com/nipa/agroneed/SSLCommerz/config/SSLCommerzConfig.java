package com.nipa.agroneed.SSLCommerz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sslcommerz")
public class SSLCommerzConfig {
    private String storeId;
    private String storePassword;
    private boolean sandbox;
    private String url;
}