package com.baeldung.testing.assertj.custom;

import org.assertj.core.api.AbstractAssert;

public class CarAssert extends AbstractAssert<CarAssert, Car> {

    public CarAssert(Car actual) {
        super(actual, CarAssert.class);
    }

    public static CarAssert assertThat(Car actual) {
        return new CarAssert(actual);
    }

    public CarAssert hasType(String type) {
        isNotNull();
        if (!actual.getType().equals(type)) {
            failWithMessage("Expected type %s but was %s", type, actual.getType());
        }
        return this;
    }

    public CarAssert isUsed() {
        isNotNull();
        if (actual.getOwner() == null) {
            failWithMessage("Expected old but was new");
        }
        return this;
    }
}
