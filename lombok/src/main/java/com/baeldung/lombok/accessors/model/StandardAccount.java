package com.baeldung.lombok.accessors.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StandardAccount {
    private String name;
    private BigDecimal balance;
}
