package com.baeldung.pattern.hexagonal.product.application;

import com.baeldung.pattern.hexagonal.product.application.port.inbound.GetPriceUseCase;
import com.baeldung.pattern.hexagonal.product.application.port.outbound.LoadProductPort;
import com.baeldung.pattern.hexagonal.product.domain.Product;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class ProductService implements GetPriceUseCase {

    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.9");

    private LoadProductPort loadProductPort;

    public ProductService(LoadProductPort loadProductPort) {
        this.loadProductPort = loadProductPort;
    }

    public BigDecimal getPrice(String productId) {
        Product product = loadProductPort.getById(productId);

        return discountedPriceFor(product);
    }

    private BigDecimal discountedPriceFor(Product product) {
        if (product == null) {
            return ZERO;
        } else if (product.isOnSale()) {
            return product.getPrice().multiply(DISCOUNT_RATE);
        } else {
            return product.getPrice();
        }
    }
}
