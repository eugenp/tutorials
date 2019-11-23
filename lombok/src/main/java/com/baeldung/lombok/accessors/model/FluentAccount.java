package com.baeldung.lombok.accessors.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(fluent = true, chain = false)
@Getter
@Setter
public class FluentAccount {
    private String name;
    private BigDecimal balance;
}
