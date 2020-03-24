package com.baeldung.autovalue;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AutoValueMoney {
    public abstract String getCurrency();

    public abstract long getAmount();

    public static AutoValueMoney create(String currency, long amount) {
        return new AutoValue_AutoValueMoney(currency, amount);

    }
}
