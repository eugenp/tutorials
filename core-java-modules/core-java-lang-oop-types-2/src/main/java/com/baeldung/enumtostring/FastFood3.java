package com.baeldung.enumtostring;

public enum FastFood3 {
    PIZZA("Pizza Pie"),
    BURGER("Cheese Burger"),
    TACO("Crunchy Taco"),
    CHICKEN("Fried Chicken"),
    ;

    private final String prettyName;

    FastFood3(String prettyName) {
        this.prettyName = prettyName;
    }

    FastFood3 fromString(String prettyName) {
        for (FastFood3 f : values()) {
            if (f.prettyName.equals(prettyName)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return prettyName;
    }
}
