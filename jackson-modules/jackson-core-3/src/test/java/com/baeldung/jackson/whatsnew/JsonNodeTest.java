package com.baeldung.jackson.whatsnew;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonNodeTest {

    @Test
    void givenJson_whenReadTree_thenValueAccessorEnhanced() {
        var json = "{\"product\":\"Laptop\",\"price\":1200}";
        var root = MapperFactory.getMapper().readTree(json);

        var product = root.get("product").asString();
        var price = root.get("price").intValueOpt();

        assertThat(product)
            .isEqualTo("Laptop");
        assertThat(price)
            .isEqualTo(OptionalInt.of(1200));
    }
}
