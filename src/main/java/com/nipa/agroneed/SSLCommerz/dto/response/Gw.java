package com.nipa.agroneed.SSLCommerz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gw {
    public String visa;
    public String master;
    public String amex;
    public String othercards;
    public String internetbanking;
    public String mobilebanking;

}