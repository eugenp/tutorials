package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private final Logger logger = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceRepository priceRepository;

    @GetMapping(path = "/price/{id}")
    public Price getPrice(@PathVariable("id") long productId) {
        logger.info("Getting Price details for Product Id {}", productId);
        return priceRepository.getPrice(productId);
  }
}
