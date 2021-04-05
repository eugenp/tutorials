package com.baeldung.pattern.hexagonal.product.application;

import com.baeldung.pattern.hexagonal.product.application.port.outbound.LoadProductPort;
import com.baeldung.pattern.hexagonal.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


class ProductServiceUnitTest {

    private ProductService productService;

    private LoadProductPort mockLoadProductPort;

    @BeforeEach
    void setup() {
        mockLoadProductPort = mock(LoadProductPort.class);
        productService = new ProductService(mockLoadProductPort);
    }

    @Test
    void givenProductIsOnSale_whenQueryingForPrice_thenDiscountedPriceIsReturned() {
        Product product = givenProductIsOnSale();
        given(mockLoadProductPort.getById(product.getId())).willReturn(product);

        BigDecimal price = productService.getPrice(product.getId());

        assertThat(price).isEqualTo(expectedDiscountedPrice());
    }

    private static Product givenProductIsOnSale() {
        return new Product("id1", true, BigDecimal.TEN);
    }

    private static BigDecimal expectedDiscountedPrice() {
        return BigDecimal.TEN.multiply(new BigDecimal("0.9"));
    }

}