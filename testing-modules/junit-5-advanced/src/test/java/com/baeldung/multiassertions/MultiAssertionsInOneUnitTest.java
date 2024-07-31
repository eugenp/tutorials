package com.baeldung.multiassertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class MultiAssertionsInOneUnitTest {
    private static final Product EXPECTED = new Product(42L, "LG Monitor", "32 inches, 4K Resolution, Ideal for programmer", true, new BigDecimal("429.99"), 77);
    private static final Product TO_BE_TESTED = new Product(-1L, "LG Monitor", "dummy value: whatever", true, new BigDecimal("429.99"), 77);

    @Test
    void givenAProductObject_whenUsingAssertAll_thenAssertMultiPropertiesInOneCall() {
        //@formatter:off
        assertAll("Verify Product properties",
          () -> assertEquals(EXPECTED.getName(), TO_BE_TESTED.getName()),
          () -> assertEquals(EXPECTED.isOnSale(), TO_BE_TESTED.isOnSale()),
          () -> assertEquals(EXPECTED.getStockQuantity(), TO_BE_TESTED.getStockQuantity()),
          () -> assertEquals(EXPECTED.getPrice(), TO_BE_TESTED.getPrice()));
        //@formatter:on
    }

    @Test
    void givenAProductObject_whenUsingAssertJExtracting_thenAssertMultiPropertiesInOneCall() {
        //@formatter:off
        assertThat(TO_BE_TESTED)
          .extracting("name", "onSale", "stockQuantity", "price")
          .containsExactly(EXPECTED.getName(), EXPECTED.isOnSale(), EXPECTED.getStockQuantity(), EXPECTED.getPrice());

        assertThat(TO_BE_TESTED)
          .extracting(Product::getName, Product::isOnSale, Product::getStockQuantity,Product::getPrice)
          .containsExactly(EXPECTED.getName(), EXPECTED.isOnSale(), EXPECTED.getStockQuantity(), EXPECTED.getPrice());
        //@formatter:on
    }

    @Test
    void givenAProductObject_whenUsingAssertJReturns_thenAssertMultiPropertiesInOneCall() {
        //@formatter:off
        assertThat(TO_BE_TESTED)
          .returns(EXPECTED.getName(),from(Product::getName))
          .returns(EXPECTED.isOnSale(), from(Product::isOnSale))
          .returns(EXPECTED.getStockQuantity(), from(Product::getStockQuantity))
          .returns(EXPECTED.getPrice(), from(Product::getPrice))

          .doesNotReturn(EXPECTED.getId(), from(Product::getId))
          .doesNotReturn(EXPECTED.getDescription(), from(Product::getDescription));
        //@formatter:on
    }
}

class Product {
    private Long id;
    private String name;
    private String description;
    private boolean onSale;
    private BigDecimal price;
    private int stockQuantity;

    public Product(Long id, String name, String description, boolean onSale, BigDecimal price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.onSale = onSale;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}