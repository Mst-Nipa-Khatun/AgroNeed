package com.nipa.agroneed.SSLCommerz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Desc {

    public String name;
    public String type;
    public String logo;
    public String gw;
    public String rFlag;
    public String redirectGatewayURL;

}