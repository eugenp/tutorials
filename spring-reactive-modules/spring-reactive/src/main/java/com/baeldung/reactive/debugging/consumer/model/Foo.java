package com.baeldung.reactive.debugging.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foo {

    private Integer id;
    private String formattedName;
    private Integer quantity;

    public Foo(FooDto dto) {
        this.id = randomId() == 0 ? null : dto.getId();
        this.formattedName = dto.getName();
        this.quantity = randomQuantity();
    }

    private static int randomId() {
        return ThreadLocalRandom.current().nextInt(0, 100);
    }

    private static int randomQuantity() {
        return ThreadLocalRandom.current().nextInt(0, 10);
    }
}
