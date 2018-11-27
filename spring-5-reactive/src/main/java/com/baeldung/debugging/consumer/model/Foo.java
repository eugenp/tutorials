package com.baeldung.debugging.consumer.model;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foo {

    @Id
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
