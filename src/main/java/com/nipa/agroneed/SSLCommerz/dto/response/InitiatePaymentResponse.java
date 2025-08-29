package com.nipa.agroneed.SSLCommerz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiatePaymentResponse {

    public String status;
    public String failedreason;
    public String sessionkey;
    public Gw gw;
    public String redirectGatewayURL;
    public String directPaymentURLBank;
    public String directPaymentURLCard;
    public String directPaymentURL;
    public String redirectGatewayURLFailed;
    public String GatewayPageURL;
    public String storeBanner;
    public String storeLogo;
    public String store_name;
    public List<Desc> desc;
    public String is_direct_pay_enable;

}