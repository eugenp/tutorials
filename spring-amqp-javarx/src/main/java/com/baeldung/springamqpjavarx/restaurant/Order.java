package com.baeldung.springamqpjavarx.restaurant;

public enum Order {
    PIZZA, ICE_CREAM, SODA;

    public static Order getByCode(String code) throws NoSuchOrderException {
        try {
            return valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchOrderException();
        }
    }

    public static class NoSuchOrderException extends Exception {}
}
