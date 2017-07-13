package com.baeldung.autovalue;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AutoValueMoneyWithBuilder {
    public abstract String getCurrency();

    public abstract long getAmount();

    static Builder builder() {
        return new AutoValue_AutoValueMoneyWithBuilder.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setCurrency(String currency);

        abstract Builder setAmount(long amount);

        abstract AutoValueMoneyWithBuilder build();
    }
}
