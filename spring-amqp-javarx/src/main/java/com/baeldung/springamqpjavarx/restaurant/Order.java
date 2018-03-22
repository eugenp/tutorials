package com.baeldung.springamqpjavarx.restaurant;

public enum Order {
    PIZZA, ICE_CREAM, SODA;

    public static Order getByCode(String code) throws NoSuchOrderException {
        code = code.toUpperCase();
        for (Order order : values()) {
            if (code.equals(order.name())) {
                return order;
            }
        }
        throw new NoSuchOrderException();
    }

    public static class NoSuchOrderException extends Exception {}
}
