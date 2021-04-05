package com.baeldung.pattern.hexagonal.product.adapter.inbound.web;

import com.baeldung.pattern.hexagonal.product.application.port.inbound.GetPriceUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ProductController {

    private GetPriceUseCase getPriceUseCase;

    public ProductController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping("/products/{productId}/price")
    public PriceResource getPrice(@PathVariable("productId") String productId) {
        // perform required validation

        BigDecimal price = getPriceUseCase.getPrice(productId);

        return new PriceResource(productId, price);
    }
}
