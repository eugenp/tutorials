package com.baeldung.springcloudgateway.oauth.backend.domain;

import lombok.Data;

@Data
public class Quote {
    
    private String symbol;
    private double price;

}
