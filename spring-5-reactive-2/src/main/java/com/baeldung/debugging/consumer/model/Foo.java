package com.baeldung.debugging.consumer.model;

import java.util.concurrent.ThreadLocalRandom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
