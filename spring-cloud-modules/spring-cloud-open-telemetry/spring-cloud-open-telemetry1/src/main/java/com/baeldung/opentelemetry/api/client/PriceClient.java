package com.baeldung.opentelemetry.api.client;

import com.baeldung.opentelemetry.model.Price;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceClient.class);

    private final RestTemplate restTemplate;

    @Autowired
    public PriceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${priceClient.baseUrl}")
    private String baseUrl;

    public Price getPrice(@PathVariable("id") long productId){
        LOGGER.info("Fetching Price Details With Product Id {}", productId);
        String url = String.format("%s/price/%d", baseUrl, productId);
        ResponseEntity<Price> price = restTemplate.getForEntity(url, Price.class);
        return price.getBody();
    }
}
