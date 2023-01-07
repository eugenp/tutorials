package com.baeldung.opentelemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Component
public class PriceClient {

    private static final Logger logger = LoggerFactory.getLogger(PriceClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${priceClient.baseUrl}")
    private String baseUrl;

    public Price getPrice(@PathVariable("id") long productId){
        logger.info("Fetching Price Details With Product Id {}", productId);
        String url = String.format("%s/price/%d", baseUrl, productId);
        ResponseEntity<Price> price = restTemplate.getForEntity(url, Price.class);
        return price.getBody();
    }
}
