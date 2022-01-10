package com.baeldung.reactive.debugging.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Foo {

    private Integer id;
    private String formattedName;
    private Integer quantity;

    public Foo(FooDto dto) {
        this.id = (ThreadLocalRandom.current()
            .nextInt(0, 100) == 0) ? null : dto.getId();
        this.formattedName = dto.getName();
        this.quantity = ThreadLocalRandom.current()
            .nextInt(0, 10);
    }
}
