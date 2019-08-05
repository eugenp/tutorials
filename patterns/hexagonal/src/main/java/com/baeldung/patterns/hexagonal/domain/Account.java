package com.baeldung.patterns.hexagonal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Account {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private int fundsLeft;

}
