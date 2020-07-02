package org.baeldung.conditionalflow.model;

import java.util.Objects;

public class NumberInfo {
    private int number;

    public NumberInfo(int number) {
        this.number = number;
    }

    public static NumberInfo from(int number) {
        return new NumberInfo(number);
    }

    public boolean isPositive() {
        return number > 0;
    }

    public boolean isEven() {
        return number % 2 == 0;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NumberInfo that = (NumberInfo) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "NumberInfo{" + "number=" + number + '}';
    }
}