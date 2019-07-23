package com.baeldung.validation.listvalidation.model;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Actor {

    private String id;
    private String name;

    public Actor(String name) {
        this.id = UUID.randomUUID()
            .toString();
        this.name = name;
    }
}
