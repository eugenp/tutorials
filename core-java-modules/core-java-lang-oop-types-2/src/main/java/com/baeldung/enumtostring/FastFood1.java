package com.baeldung.enumtostring;

public enum FastFood1 {
    PIZZA,
    BURGER,
    TACO,
    CHICKEN,
    ;

    @Override
    public String toString() {
        switch (this) {
        case PIZZA:
            return "Pizza Pie";
        case BURGER:
            return "Cheese Burger";
        case TACO:
            return "Crunchy Taco";
        case CHICKEN:
            return "Fried Chicken";
        default:
            return "";
        }
    }
}
