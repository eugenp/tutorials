package com.baeldung.hexarch.adapters.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SwipeRequest {
    private boolean isCash;
    private BigDecimal amount;
    private boolean isDebit;
}
