package com.baeldung.lombok.accessors.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(makeFinal = true, fluent = true, chain = true)
@Getter
@Setter
public class FinalChainedFluentAccount {
    private String name;
    private BigDecimal balance;
}