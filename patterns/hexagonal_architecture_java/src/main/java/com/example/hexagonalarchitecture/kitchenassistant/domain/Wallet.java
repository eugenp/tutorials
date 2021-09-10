package com.example.hexagonalarchitecture.kitchenassistant.domain;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Wallet {

    @NonNull BigDecimal balance;

    public boolean isPositiveBalance(){
        return this.balance.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean balanceCanBuy(BigDecimal value){
        return this.balance.compareTo(value) >= 1;
    }

    public static Wallet of(long value) {
        return new Wallet(BigDecimal.valueOf(value));
    }



}
