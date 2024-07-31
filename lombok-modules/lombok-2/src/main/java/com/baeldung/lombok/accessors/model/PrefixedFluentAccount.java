package com.baeldung.lombok.accessors.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(prefix = {"s", "bd"}, fluent = true)
@Getter
@Setter
public class PrefixedFluentAccount {
    private String sName;
    private BigDecimal bdBalance;
}
